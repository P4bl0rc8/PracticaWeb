document.addEventListener("DOMContentLoaded", function(){ loadItems();})

function updateUser(){

    getJSessionID();

    var username = document.getElementById('username').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    var csrf_token = document.getElementById('_csrf').content;
    var csrf_header = document.getElementById('_csrf_header').content;

    var user =
        {
            "username":username,
            "email":email,
            "password":password
        };

    var clientRequest = new XMLHttpRequest();
    clientRequest.responseType = "json";
    clientRequest.addEventListener("load", function() {
        console.load(this.response);
        alert(this.response);
        console.log(clientRequest.status);

        loadItems();
    });
    clientRequest.open("DELETE", "/api/users/{username}")
    clientRequest.setRequestHeader(csrf_header, csrf_token);
    clientRequest.setRequestHeader("Content-type", "application/json");
    var body = JSON.stringify(user);
    clientRequest.send(body);

}

function updateEvent(){

    getJSessionID();

    var name = document.getElementById('name').value;
    var cod = document.getElementById('cod').value;
    var date = document.getElementById('date').value;
    var gender = document.getElementById('gender').value;
    var description = document.getElementById('description').value;
    var csrf_token = document.getElementById('_csrf').content;
    var csrf_header = document.getElementById('_csrf_header').content;

    var event =
        {
            "name":name,
            "cod":cod,
            "date":date,
            "gender":gender,
            "description":description
        };

    var clientRequest = new XMLHttpRequest();
    clientRequest.responseType = "json";
    clientRequest.addEventListener("load", function() {
        console.load(this.response);
        alert(this.response);
        console.log(clientRequest.status);

        loadItems();
    });
    clientRequest.open("DELETE", "/api/events/{cod}")
    clientRequest.setRequestHeader(csrf_header, csrf_token);
    clientRequest.setRequestHeader("Content-type", "application/json");
    var body = JSON.stringify(event);
    clientRequest.send(body);

}


function getJSessionID(){
    var jsId = document.cookie.match(/JSESSIONID=[^;]+/);
    if(jsId != null) {
        if (jsId instanceof Array)
            jsId = jsId[0].substring(11);
        else
            jsId = jsId.substring(11);
    }
    return jsId;
}

function addItemsToPage() {
    console.log("Ha funcionado");
}
function loadItems(){
    console.log("Load Correcto")
}
