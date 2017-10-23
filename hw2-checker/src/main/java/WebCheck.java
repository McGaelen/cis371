//package edu.gvsu.cs371;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.*;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by Hans Dulimarta on Sep 07, 2017.
 */
public class WebCheck {

    private static Document doc;
    private static Map<String, List<String>> rulesMap;
    private Element mainTable, courseTable;

    @BeforeClass
    public static void setup() {
        try {
            rulesMap = new TreeMap<>();
//      File inputFile = new File("sample-page.xhtml");
            String url = System.getProperty("TOPURL");
            URL pageURL = new URL(url);
//      doc = Jsoup.parse(inputFile, "UTF-8");
            doc = Jsoup.parse(pageURL, 3000);
            Elements links = doc.head()
                    .getElementsByAttributeValueMatching("rel", "stylesheet");
            Element styleLink = null;
            for (Element el : links) {
                if (el.tagName().equals("link")) {
                    styleLink = el;
                    break;
                }
            }
            assertNotNull(styleLink);
            String styleFilename = styleLink.attr("href");
            if (styleFilename.startsWith("http"))
                parseRules(styleFilename);
            else {
                StringBuilder builder = new StringBuilder();
                builder.append(pageURL.getProtocol())
                        .append("://")
                        .append(pageURL.getHost())
                        .append(":")
                        .append(pageURL.getDefaultPort());
                if (styleFilename.startsWith("/"))
                    builder.append(styleFilename);
                else {
                    int lastSlashPos = pageURL.getFile().lastIndexOf("/");
                    builder.append(pageURL.getFile().substring(0, lastSlashPos + 1))
                            .append(styleFilename);
                }
                parseRules(builder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Before
    public void getTable() {
        mainTable = doc.getElementById("mainTable");
        assertNotNull("Missing mainTable", mainTable);
        courseTable = doc.getElementById("courseTable");
        assertNotNull("Missing courseTable", courseTable);
    }

    private static void parseRules (String styleURL) {
        try {
//      InputStream styleFile = new FileInputStream(filename);
            URL sURL = new URL(styleURL);
            InputSource iSource = new InputSource();
            iSource.setByteStream(sURL.openStream());
//      iSource.setByteStream(styleFile);
            iSource.setEncoding("UTF-8");
            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
            CSSStyleSheet css;
            css = parser.parseStyleSheet(iSource, null, null);
            CSSRuleList ruleList = css.getCssRules();
            assertTrue("CSS file \"" + styleURL + "\" contains no CSS rules",
                    ruleList.getLength() > 0);
            for (int k = 0; k < ruleList.getLength(); k++) {
                CSSRule rule = ruleList.item(k);
                switch (rule.getType()) {
                    case CSSRule.STYLE_RULE:
                        CSSStyleRule stylerule = (CSSStyleRule) rule;
                        CSSStyleDeclaration declarations = stylerule.getStyle();
                        String selector = stylerule.getSelectorText();
                        assertTrue("CSS selector \"" + selector +
                                        "\" contains no style declarations",
                                declarations.getLength() > 0);
                        List<String> props = new ArrayList<>();
                        for (int m = 0; m < declarations.getLength(); m++) {
                            String prop = declarations.item(m);
                            String propVal = declarations.getPropertyValue(prop);
                            props.add(prop + ":" + propVal);
                        }
                        rulesMap.put(selector, props);
                        break;
                    default:
                        fail("Unhandled CSS rule type " + rule.getType());
                }
            }

        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private boolean hasKeyValue (String declaration, String key, String value)
    {
        String[] toks = declaration.split(":");
        return toks[0].contains(key) && toks[1].contains(value);
    }

    private boolean hasAncestorWithId(Element el, String ID) {
        boolean found = false;
        Element par = el;
        while (!found && par != null) {
            if (ID.equals(par.id()))
                found = true;
            else
                par = par.parent();
        }
        return found;
    }

    @Test
    public void htmlHasNamespaceAttribute()
    {
        Element html = doc.child(0);
        assertTrue (html.hasAttr("xmlns"));
    }

    @Test
    public void hasHeaderAndBody() {
        assertTrue(doc.head().html().length() > 0);
        assertTrue(doc.body().html().length() > 0);
    }

    @Test
    public void useExternalStyleSheet() {
        Element head = doc.head();
        Elements links = head.getElementsByAttributeValueMatching("rel", "stylesheet");
        assertTrue (links.size() > 0);
        Element styeLink = null;
        for (Element e : links) {
            if (e.tagName().equals("link")) {
                styeLink = e;
                break;
            }
        }
        assertNotNull(styeLink);
        assertTrue(styeLink.hasAttr("href"));
        assertTrue(styeLink.attr("href").length() > 0);
    }

    @Test
    public void hasHeadingAtTop() {
        for (int k = 1; k <= 6; k++) {
            Elements headings = doc.select("h" + k);
            if (headings.size() > 0)
                return;
        }
        fail("Missing heading");
    }

    @Test
    public void hasSixCellTable() {
        Elements cells = mainTable.select("table td div");
        assertTrue(cells.size() >= 6);
    }

    @Test
    public void mainTableHasNoBorder() {
        assertTrue("main Table should not have a border",
                mainTable.hasAttr("border") == false);
    }

    @Test
    public void couseTableMustHaveBorder() {
        assertTrue("courseTable must have a border",
                courseTable.hasAttr("border"));
    }

    @Test
    public void divsInTableHaveClasses() {
        Elements cells = mainTable.select("table td div");
        for (Element el : cells) {
            String className = el.attr("class").trim();
            assertTrue(className.length() > 0);
        }
    }

    @Test
    public void tableHasACellWithUnorderedListOfFavoriteThings() {
        Elements unordered = mainTable.select("div ul li");
        assertTrue("Missing list of favorite things",
                unordered.size() > 1);
    }

    @Test
    public void tableHasACellWithImage() {
        Elements inspiringImg = mainTable.select("div img");
        assertTrue("Missing image of inspiring person",
                inspiringImg.size() > 0);
    }

    @Test
    public void tableHasAlphabeticalList() {
        final String error = "Missing alphabetical list of things you do";
        Elements alphaList = mainTable.select("div ol");
        assertTrue ("Missing an ordered list in table",
                alphaList.size() > 0);
        if (alphaList.hasAttr("type")) {
            assertEquals(error, "a", alphaList.attr("type"));
            return;
        }
    /* check by ID */
        if (alphaList.hasAttr("id")) {
            String listID = "#" + alphaList.attr("id");
            List<String> props = rulesMap.get(listID);
            if (props != null && !props.isEmpty()) {
                for (String prop : props) {
                    if (hasKeyValue(prop, "list-style", "lower-alpha"))
                        return;
                }
            }
        }
    /* check by class */
        if (alphaList.hasAttr("class")) {
            String className = "." + alphaList.attr("class");
            List<String> props = rulesMap.get(className);
            if (props != null && !props.isEmpty()) {
                for (String prop : props) {
                    if (hasKeyValue(prop, "list-style", "lower-alpha"))
                        return;
                }
            }
        }

    /* check by reverse lookup
       (1) location declaration "list-style:lower-alpha"
       (2) use selector of the rule leads to the mainTable
     */
        for (String selector : rulesMap.keySet()) {
            List<String> props = rulesMap.get(selector);
            for (String decl : props) {
                String[] toks = decl.split(":");
                if (toks[0].startsWith("list-style") && toks[1].startsWith("lower-alpha")) {
                    Elements elems = doc.select(selector);
                    for (Element el : elems) {
                        if (hasAncestorWithId(el, "mainTable"))
                            return;
                    }
                }
            }
        }
        fail(error);
    }

    @Test
    public void tableHasCellWithLinks() {
        Elements hyperLinks = mainTable.select("div a");
        assertTrue(hyperLinks.size() > 0);
        for (Element anchor : hyperLinks) {
      /* must be an external web site */
            assertTrue("Anchor URL must begin with http",
                    anchor.attr("href").startsWith("http"));
        }
        Element el = hyperLinks.first();
        while (!el.tagName().equals("div"))
            el = el.parent();
        assertTrue("<div> of hyperlinks must include a class \"links\"",
                el.attr("class").contains("links"));
    }

    @Test
    public void tableHasCellWithQuotation() {
        Elements shortQuotes = mainTable.select("div q");
        Elements longQuotes = mainTable.select("div blockquote");
        assertTrue ("Missing favorite Latin quotations",shortQuotes.size() + longQuotes.size() > 0);
    }

    @Test
    public void allImgMustIncludeAltAttribute() {
        Elements images = mainTable.select("img");
        for (Element el : images)
            assertTrue ("<img> must define alt attribute", el.hasAttr("alt"));

    }

    @Test
    public void styleFileContainNonEmptyRules() {
        assertTrue(rulesMap.size() > 0);
        for (String sel : rulesMap.keySet()) {
            List<String> props = rulesMap.get(sel);
            assertTrue(props.size() > 0);
        }
    }

    @Test
    public void allRulesAreAppliedToHTMLPage() {
        for (String sel : rulesMap.keySet()) {
            Elements elems = doc.select(sel);
            assertTrue ("Unused selector \"" + sel + "\"",
                    elems.size() > 0);
        }
    }

    @Test
    public void pageFontMustBeTimes() {
        List<String> bodyProps = rulesMap.get("body");
        assertNotNull("Missing style for <body>", bodyProps);
        for (String prop : bodyProps)
            if (hasKeyValue(prop.toLowerCase(), "font-family", "times"))
                return;
        fail("Must apply font Times to the entire page");
    }


    @Test
    public void mustDefineClassesGivenClasses() {
        String[] requiredClasses = {"best", "links"};
        for (String className : requiredClasses) {
            boolean found = false;
            for (String key : rulesMap.keySet()) {
                if (key.contains(className)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                fail("Class \"" + className + "\" is not defined in your CSS");
        }
    }

    @Test
    public void bestClassMustItalicizeText() {
        List<String> bestProps = rulesMap.get(".best");
        for (String prop : bestProps)
            if (hasKeyValue(prop, "font-style", "italic"))
                return;
        fail("Class \"best\" must italicize the text");
    }

    @Test
    public void paragraphInTableInSmallFont() {
        for (String selector : rulesMap.keySet()) {
            List<String> props = rulesMap.get(selector);
            for (String decl : props) {
                String[] toks = decl.split(":");
                if (toks[0].startsWith("font-size") && toks[1].contains("small")) {
                    Elements elems = doc.select(selector);
                    for (Element el : elems) {
                        if (hasAncestorWithId(el, "mainTable"))
                            return;
                    }
                }
            }
        }
        fail("Paragraph inside tables must use small font");

    }

    @Test
    public void marginAroundPersonalImage() {
        Elements personalImg = doc.select("body > img");
        assertTrue("Personal image is not found as a child of <body>",
                personalImg.size() > 0);
        List<Element> images = new ArrayList<>();
        images.addAll(personalImg.stream().collect(Collectors.toList()));
        Set<Element> elementsWithMargin = new TreeSet<>();
        for (String selector : rulesMap.keySet()) {
            List<String> declList = rulesMap.get(selector);
            for (String decl : declList) {
                if (decl.contains("margin")) {
                    for (Element el : doc.select(selector))
                        if (images.contains(el))
                            return;
                }
            }
        }
        fail("Personal image must have margins");
    }

    @Test
    public void noInlineStyles() {
        Elements inline = doc.select("[style]");
        assertTrue("All styles should be defined in the external file",
                inline.size() == 0);
    }

    @Test
    public void classInHTMLMustBeDefinedInCss() {
        Elements elems = doc.select("[class]");
        for (Element el : elems) {
            String[] klas = el.attr("class").trim().split(" +");
            for (String className : klas) {
                boolean isDefined = false;
                for (String sel : rulesMap.keySet()) {
                    if (sel.contains("." + className)) {
                        isDefined = true;
                        break;
                    }
                }
                if (!isDefined) {
                    fail("No CSS rule for class \"" + className + "\"");
                }
            }
        }
    }

    @Test
    public void courseTableMustUseNthChildPseudoClass() {
        Set<String> nthChildSelectors = new TreeSet<>();
        for (String selector : rulesMap.keySet()) {
            if (selector.contains(":nth-child")) {
        /* Look up elements */
                Elements elems = doc.select(selector);
                for (Element el : elems) {
                    if (hasAncestorWithId(el, "courseTable")) {
                        nthChildSelectors.add(selector);
                        break;
                    }
                }

            }
        }
        assertTrue("Missing :nth-child pseudo classes?",
                nthChildSelectors.size() >= 3);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            System.setProperty("TOPURL", args[0]);
            JUnitCore.main("WebCheck");
        }
        else {
            System.err.println("java -cp ..... edu.gvsu.cs371.WebCheck http://your.host.name/path/to/your/webpage.html");
        }
    }
}