/**
 * view-controller for werkzeugedit.html
 *
 * M133: Kammerjeager
 *
 * @author  Aaron Perez
 */

/**
 * register listeners and load the werkzeug data
 */
$(document).ready(function () {
    loadWerkzeuge();

    /**
     * listener for submitting the form
     */
    $("#werkzeugeditForm").submit(function (event){saveWerkzeug(event);});

    /**
     * listener for button [abbrechen], redirects to kammerjeager
     */
    $("#cancel").click(function () {
        window.location.href = "kammerjeager.html";
    });


});

/**
 *  loads the data of this werkzeug
 *
 */
function loadWerkzeuge() {
    let werkzeugUUID = $.urlParam("werkzeugUUID");
    if (werkzeugUUID) {
        $
            .ajax({
                url: "./resource/werkzeug/read?werkzeugUUID=" + werkzeugUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showWerkzeug())
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 403) {
                    window.location.href = "./login.html";
                } else if (xhr.status == 404) {
                    $("#message").text("Kein Werkzeug gefunden");
                } else {
                    window.location.href = "../kammerjeager.html";
                }
            })
    }

}

/**
 * shows the data of this werkzeug
 * @param  werkzeug  the werkzeug data to be shown
 */
function showWerkzeug(werkzeug) {
    $("#werkzeugUUID").val(werkzeug.werkzeugUUID);
    $("#name").val(werkzeug.name);
    $("#type").val(werkzeug.type);
    $("#attribut").val(werkzeug["attribut"]);
    $("#values").val(werkzeug.values);
    $("#effectiveness").val(werkzeug.effectiveness);
    $("#releaseDate").val(werkzeug.releaseDate);

}

/**
 * sends the werkzeug data to the webservice
 * @param form the form being submitted
 */
function saveWerkzeug(form) {
    form.preventDefault();

    let url = "./resource/werkzeug/";
    let type;

    let werkzeugUUID = $("#werkzeugUUID").val();
    if (werkzeugUUID) {
        type= "PUT";
        url += "update"
    } else {
        type = "POST";
        url += "create";
    }

    $
        .ajax({
            url: url,
            dataType: "text",
            type: type,
            data: $("#werkzeugeditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "../kammerjeager.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Werkzeug existiert nicht");
            } else {
                $("#message").text("Fehler beim Speichern vom Werkzeug");
            }
        })

}
