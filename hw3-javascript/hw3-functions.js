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
    /* complete this function */
}