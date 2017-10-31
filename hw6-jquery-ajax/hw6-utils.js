const prefix = 'https://www.googleapis.com/calendar/v3';
const apiKey = 'AIzaSyB4s-Dl-HjQdcoWJ0uD_wyb9SxORCguDN4';
const id = 'mcinteeg@mail.gvsu.edu';

function renderEvents(eventsList) {
    let table = $('#calendar-table');
    $.each(eventsList, (index, event) => {
        let tr = $('<tr>');
        tr.append($('<td>').append(event.start.dateTime.toString()));
        tr.append($('<td>').append(event.end.dateTime.toString()));
        tr.append($('<td>').append(event.summary));
        tr.append($('<td>').append(event.organizer.email));
        table.append(tr);
    });
}