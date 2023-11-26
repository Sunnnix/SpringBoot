document.getElementById("createForm").addEventListener("submit", function (event) {
    event.preventDefault();

    var name1 = document.getElementById("name1").value;
    var name2 = document.getElementById("name2").value;
    var age = document.getElementById("age").value;

    fetch("http://localhost:8080/create?name1=" + name1 + "&name2=" + name2 + "&age=" + age, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => response.json())
    .then(data => {
        // Ergebnis in die Liste einfügen
        var resultList = document.getElementById("resultList");
        var listItem = document.createElement("li");
        listItem.innerHTML = "ID: " + data.id + "<br>Name: " + data.firstName + " " + data.lastName + "<br>Alter: " + data.age;
        resultList.insertBefore(listItem, resultList.firstChild); // Element oben in die Liste einfügen
    })
    .catch(error => {
        console.error("Fehler bei der AJAX-Anfrage:", error);
    });
});

document.getElementById("searchButton").addEventListener("click", function () {
    var searchName = document.getElementById("searchName").value;

    fetch("http://localhost:8080/with-firstname/" + searchName)
    .then(response => response.json())
    .then(data => {
        var searchResultList = document.getElementById("searchResultList");
        searchResultList.innerHTML = ""; // Vorherige Ergebnisse löschen

        if (data.length === 0) {
            searchResultList.innerHTML = "Keine Ergebnisse gefunden.";
        } else {
            data.forEach(item => {
                var listItem = document.createElement("li");
                listItem.innerHTML = "ID: " + item.id + "<br>Name: " + item.firstName + " " + item.lastName + "<br>Alter: " + item.age;
                searchResultList.appendChild(listItem);
            });
        }
    })
    .catch(error => {
        console.error("Fehler bei der Suchanfrage:", error);
    });
});