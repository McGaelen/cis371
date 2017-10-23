/**
 * Created by Hans Dulimarta Fall 2017.
 * Gaelen McIntee
 */

/**
 * Given a node with id {rootId}, the following function finds all its descendant
 * elements having its attribute ID set. The function returns the number of
 * such elements. ALSO, for each such element this function sets its class
 * to {klazName}.
 *
 * @param rootId
 * @returns {number}
 */
function findElementsWithId(rootId, klazname) {
    let counter = 0;
    document.querySelectorAll(`#${rootId} > [id]`).forEach( elem => {
        counter++;
        elem.className += klazname;
    });
    return counter;
}


/**
 * The following function finds all elements with attribute 'data-gv-row' (or
 * 'data-gv-column') and create a table of the desired dimension as a child of
 * the element.
 *
 * @returns NONE
 */
function createTable() {
    let tableDiv = document.querySelector('.table-home');
    let rows = Number(tableDiv.getAttribute('data-gv-row'));
    let cols = Number(tableDiv.getAttribute('data-gv-column'));

    const lipsum = new LoremIpsum();

    tableDiv.innerHTML = '<table id="table"></table>';
    let table = document.getElementById('table');

    for (let i = 0; i < rows+1; i++) {
        let newRow = document.createElement('tr');
        table.appendChild(newRow);
        for (let j = 0; j < cols; j++) {
            if (i === 0) {
                let newHeading = document.createElement('th');
                newHeading.appendChild(document.createTextNode(`Heading ${j+1}`));
                newRow.appendChild(newHeading);
            } else {
                let newCol = document.createElement('td');
                newCol.appendChild(document.createTextNode(lipsum.generate(3)));
                newRow.appendChild(newCol);
            }
        }
    }
}