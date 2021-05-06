/**
 * view-controller for kammerjeager.html
 *
 * M133: Kammerjeager
 *
 * @author  Aaron Perez
 */

/**
 * register listeners and load all werkzeuge
 */
$(document).ready(function () {
    loadWerkzeuge();

    /**
     * listener for buttons within shelfForm
     */
    $("#shelfForm").on("click", "button", function () {
        if (confirm("Werkzeug löschen?")) {
            deleteWerkzeug(this.value);
        }
    });



});

function loadWerkzeuge() {
    $
        .ajax({
            url: "./resource/werkzeug/list",
            dataType: "json",
            type: "GET"
        })
        .done(showWerkzeuge())
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href = "./login.html";
            } else if (xhr.status == 404) {
                $("#message").text("kein Werkzeug vorhanden");
            }else {
                $("#message").text("Fehler beim Lesen vom Werkzeug");
            }
        })

}

/**
 * shows all werkzeuge as a table
 *
 * @param werkzeugData all werkzeuge as an array
 */
function showWerkzeuge(werkzeugData) {

    let table = document.getElementById("werkzeuglist");
    clearTable(table);

    $.each(werkzeugData, function (uuid, werkzeug) {
        if (werkzeug) {
            let row = table.insertRow(-1);
            let cell = row.insertCell(-1);
            cell.innerHTML = werkzeug.name;

            cell = row.insertCell(-1);
            cell.innerHTML = werkzeug.type;

            cell = row.insertCell(-1);
            cell.innerHTML = werkzeug["attribut"];

            cell = row.insertCell(-1);
            cell.innerHTML = werkzeug.values;

            cell = row.insertCell(-1);
            cell.innerHTML = werkzeug.effect;

            cell = row.insertCell(-1);
            cell.innerHTML = werkzeug.releaseDate;

            cell = row.insertCell(-1);
            cell.innerHTML = "<a href='./werkzeugedit.html?werkzeugUUID=" + uuid + "'>Bearbeiten</a>";

            cell = row.insertCell(-1);
            cell.innerHTML = "<button type='button' id='delete_" + uuid + "' value='" + uuid + "'>Löschen</button>";


        }
    });
}

function clearTable(table) {
    while (table.hasChildNodes()) {
        table.removeChild(table.firstChild);
    }
}

/**
 * send delete request for a werkzeug
 * @param werkzeugUUID
 */
function deleteWerkzeug(werkzeugUUID) {
    $
        .ajax({
            url: "./resource/werkzeug/delete?werkzeugUUID=" + werkzeugUUID,
            dataType: "text",
            type: "DELETE",
        })
        .done(function (data) {
            loadWerkzeuge();
            $("#message").text("Werkzeug gelöscht");

        })
        .fail(function (xhr, status, errorThrown) {
            $("#message").text("Fehler beim Löschen vom Werkzeug");
        })
}
