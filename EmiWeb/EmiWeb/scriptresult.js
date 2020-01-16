
let params = new URLSearchParams(document.location.search.substring(1));
let search = params.get("allSearchInput");



let bla = window.location.search.substr(1);
console.log(bla);


console.log(params);

var larsson_pop = "larsson_pop_index";
var usmgbg = "usmgbg_index";
var emptyField ="\N";
var emptyField2 ="//N";
var advSearchVariable;

if(bla.includes("allSearchInput")){
  bla =search;
  advSearchVariable = "likegoogle?search=";
}
else advSearchVariable = "advanced?";


$( document ).ready(function() {

  var hackbajs = document.getElementById("jsonfan").textContent;
  var jsonbajs = JSON.parse(hackbajs);
  console.log(jsonbajs);
  console.log(jsonbajs.hits.hits[0].sourceAsMap.FirstName);

  /*******TEST JSON***********/
  
  var number_of_hits = jsonbajs.hits.totalHits;
  var data = jsonbajs.hits.hits;
  theFilterViewFunction(data,number_of_hits);
  document.getElementById("headerSearch").innerHTML = "Alla sökresultat för gris";
  /*
  document.getElementById("headerSearch").innerHTML = "Alla sökresultat för <q>"+ bla+"</q>";
            $.ajax({
                url: "http://192.168.137.211:8080/search/allindexes/"+advSearchVariable+bla,
                contentType: "application/json",
                dataType: 'json',
                success: function(result){
                  var number_of_hits = result.totalHits;
                  var data = result.hits;
                  theFilterViewFunction(data,number_of_hits);
                  var resultdis = JSON.stringify(result);
                    console.log(result);
                }
            })*/
    });



function theFilterViewFunction(data,number_of_hits){

  var startcount = 0;
  var count =  0;
  if(number_of_hits > 10)count = 10;
  else count = number_of_hits;
  if(number_of_hits != 0 ) startcount = 1;

  var y = document.getElementById("number_of_hits_h5").innerHTML = "visar "+startcount+" - "+count+ " av " +number_of_hits+" resultat";
  for(i = 0; i<count; i++){
      var x = data[i].index;
      if(x == larsson_pop){
        theResultsFunction(data[i], larsson_pop);
      }
      else if(x == usmgbg){
        theResultsFunction(data[i], usmgbg);
      }
    }
}


function theResultsFunction(data, index){
    var x = document.getElementById("resultListOL");
    newLI = document.createElement("LI");
    newLI.className = "resultListLI";

    newDiv = document.createElement("div");
    newDiv.className = "resultDivContainer";

    childDiv1 = document.createElement("div");
    childDiv2 = document.createElement("div");
    childDiv1.className = "resultDivHead";
    childDiv2.className = "resultDivPerson";
    newHeading = document.createElement("h3");
    newHeading.setAttribute("class","resultHeading");
    newHeading.innerHTML = index;
    childDiv1.appendChild(newHeading);
    data = data.sourceAsMap;

    switch (index) {
      case larsson_pop:
        resultTable = document.createElement("TABLE");
        resultTable.setAttribute("id", "tableIdResult");

        var tr = resultTable.insertRow();
        var th = document.createElement("th");
        th.innerHTML = "Förnamn: ";
        tr.appendChild(th);
        var tc = tr.insertCell();
        var a = document.createElement("a");
        var tt = document.createTextNode(data.FirstName);
        a.appendChild(tt);
        a.href = "profile.html?"+index+"="+data.Id;
        tc.appendChild(a);

        var tr = resultTable.insertRow();
        var th = document.createElement("th");
        th.innerHTML = "Efternamn: ";
        tr.appendChild(th);
        var tc = tr.insertCell();
        var tt = document.createTextNode(data.LastName);
        tc.appendChild(tt);

        if( data.HomeLocation.toString().localeCompare(emptyField.toString()) == 1){
        var tr = resultTable.insertRow();
        var th = document.createElement("th");
        th.innerHTML = "Hemort: ";
        tr.appendChild(th);
        var tc = tr.insertCell();
        var tt = document.createTextNode(data.HomeLocation);
        tc.appendChild(tt);
}
        if( data.HomeParish.toString().localeCompare(emptyField.toString()) == 1){
          var tr = resultTable.insertRow();
          var th = document.createElement("th");
          th.innerHTML = "Hemförsamling: ";
          tr.appendChild(th);
          var tc = tr.insertCell();
          var tt = document.createTextNode(data.HomeParish);
          tc.appendChild(tt);
        }
        if(data.Profession.toString().localeCompare(emptyField.toString()) == 1){
          var tr = resultTable.insertRow();
          var th = document.createElement("th");
          th.innerHTML = "Yrke: ";
          tr.appendChild(th);
          var tc = tr.insertCell();
          var tt = document.createTextNode(data.Profession);
          tc.appendChild(tt);
        }

        childDiv2.appendChild(resultTable);
        break;


      case usmgbg:
        resultTable = document.createElement("TABLE");
        resultTable.setAttribute("id", "tableIdResult");
        var tr = resultTable.insertRow();

        var th = document.createElement("th");
        th.innerHTML = "Namn: ";
        tr.appendChild(th);
        var tc = tr.insertCell();
        var tt = document.createTextNode(data.Name);
        tc.appendChild(tt);
        childDiv2.appendChild(resultTable);

        if(data.Country.toString().localeCompare(emptyField.toString()) == 1 && data.Country.toString().localeCompare(emptyField2.toString()) == 1){
          var tr = resultTable.insertRow();
          var th = document.createElement("th");
          th.innerHTML = "Land: ";
          tr.appendChild(th);
          var tc = tr.insertCell();
          var tt = document.createTextNode(data.Country);
          tc.appendChild(tt);
        }

        if(data.Profession.toString().localeCompare(emptyField.toString()) == 1){
          var tr = resultTable.insertRow();
          var th = document.createElement("th");
          th.innerHTML = "Yrke: ";
          tr.appendChild(th);
          var tc = tr.insertCell();
          var tt = document.createTextNode(data.Profession);
          tc.appendChild(tt);
        }

      break;
      default:
    }

    newLI.appendChild(newDiv);
    newDiv.appendChild(childDiv1);
    newDiv.appendChild(childDiv2);
    x.appendChild(newLI);
}

    // When the user clicks on the button, scroll to the top of the document
    function topFunction() {
      $('html,body').animate({ scrollTop: 0 }, 'slow');
    }

    function openNav() {
      document.getElementById("frontSideNav").style.width= "250px";
      document.getElementById("myNavBtn").style.display="none";
      console.log("inside open nav");
    }

    function closeNav() {
      document.getElementById("frontSideNav").style.width= "0";
      document.getElementById("myNavBtn").style.display="block";
    }
