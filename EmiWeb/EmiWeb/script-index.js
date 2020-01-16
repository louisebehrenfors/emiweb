/*THE SCRIPT FILE FOR INDEX.HTML*/
var larsson_pop = "larsson_pop_index";
var usmgbg = "usmgbg_index";
var emptyField ="\N";
var emptyField2 ="//N";
var page = 0;
var searchInputValue;
var ipaddress = "172.20.10.3:8080";

  $(document).ready(function() {
  var input_field_google_search = document.getElementById("index-input-googleSearch");
  input_field_google_search.addEventListener("keydown", function(event) {
    // Number 13 is the "Enter" key on the keyboard
    if (event.keyCode === 13) {
      // Cancel the default action, if needed
      event.preventDefault();
      // Trigger the button element with a click
      document.getElementById("button_google").click();
    }
  });
});

/*Open and closes the archieves*/
function showListFunction(click_id) {
  var x = document.getElementById(click_id);
  if (x.style.display != "block") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

window.onload = function(){
   window.onscroll = function()
   {
      scrollFunction();
   }
}

function scrollFunction() {
  var navbar = document.getElementById("myNavBtn");
  var sticky = navbar.offsetTop;

  if (window.pageYOffset > sticky) {
    navbar.style.top = "0";
  } else {
    navbar.style.top = "10vw";
  }
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


function googleSearchFunction(){
  $("#index-ol-result").html("");
  page = 0;
  var search = document.getElementById("index-input-googleSearch").value;
  if(search == null || search ==""){
    alert("tomt sökfält");
    return false;
  }
  searchInputValue = search;
  var diven = document.getElementById("index-d-result");
  diven.style.display ='table';
  $( document ).ready(function() {
    document.getElementById("headerSearch").innerHTML = "Alla sökresultat för <q>"+ search+"</q>";
              $.ajax({
                  url: "http://"+ipaddress+"/search/allindexes/likegoogle?search="+search+"&page="+page,
                  contentType: "application/json",
                  dataType: 'json',
                  success: function(result){
                    var number_of_hits = result.totalHits;
                    var data = result.hits;
                    theFilterViewFunction(data,number_of_hits,page);
                    var resultdis = JSON.stringify(result);
                    console.log(result);
                  }
              })
      });
}

function AdvancedSearchFunction(){
  $("#index-ol-result").html("");
  page = 0;
  var firstName = document.getElementById("index-adv-search-firstName").value;
  var LastName = document.getElementById("index-adv-search-LastName").value;
  var DateBirth = document.getElementById("index-adv-search-DateBirth").value;
  console.log(firstName + LastName);
  if((firstName == null || firstName =="") && (LastName == null || LastName =="") && (DateBirth == null || DateBirth =="")){
    alert("tomt sökfält");
    return false;
  }
  var diven = document.getElementById("index-d-result");
  diven.style.display ='table';

  $( document ).ready(function() {
    document.getElementById("headerSearch").innerHTML = "Alla sökresultat för <q>"+ firstName+"</q>"+" + "+"<q>"+LastName+"</q>";
              $.ajax({
                  url: "http://172.20.10.3:8080/search/allindexes/advanced?FirstName="+firstName+"&LastName="+LastName+"&page="+page,
                  contentType: "application/json",
                  dataType: 'json',
                  success: function(result){
                    var number_of_hits = result.totalHits;
                    var data = result.hits;
                    theFilterViewFunction(data,number_of_hits,page);
                    var resultdis = JSON.stringify(result);
                    console.log(result);
                  }
              })
      });

}

function theFilterViewFunction(data,number_of_hits,viewpage){

  var startcount = 0;
  var count =  0;
  var helppage = viewpage +1;

  if(number_of_hits > 10){
    count = 10*helppage;}
  else count = number_of_hits;
  if(number_of_hits != 0 ){
    startcount = count-9;
    if(startcount < 0){
      startcount = 1;
    }
  }
  var y = document.getElementById("number_of_hits_h5").innerHTML = "visar "+startcount+" - "+count+ " av " +number_of_hits+" resultat";
  for(i = 0; i<count; i++){
      var x = data[i].index;
      if(x == larsson_pop){
        theResultsFunction(data[i], larsson_pop);
      }
      else if (x == usmgbg){
        theResultsFunction(data[i], usmgbg);
      }
    }
}

function theResultsFunction(data, index){

    var x = document.getElementById("index-ol-result");
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
        a.href = "profile.html";
        var indexValue = 0;
        a.setAttribute("onclick","index_save_values("+data.Id+","+indexValue+")");
        tc.appendChild(a);

        var tr = resultTable.insertRow();
        var th = document.createElement("th");
        th.innerHTML = "Efternamn: ";
        tr.appendChild(th);
        var tc = tr.insertCell();
        var tt = document.createTextNode(data.LastName);
        tc.appendChild(tt);

        if( data.HomeLocation.toString().localeCompare(emptyField.toString()) == 1 ){
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

        var tr = resultTable.insertRow();
        var th = document.createElement("th");
        th.innerHTML = "Förnamn: ";
        tr.appendChild(th);
        var tc = tr.insertCell();
        var a = document.createElement("a");
        var tt = document.createTextNode(data.FirstName);
        a.appendChild(tt);
        a.href = "profile.html";
        var indexValue = 1;
        a.setAttribute("onclick","index_save_values("+data.Id+","+indexValue+")");
        tc.appendChild(a);

        var tr = resultTable.insertRow();
        var th = document.createElement("th");
        th.innerHTML = "Efternamn: ";
        tr.appendChild(th);
        var tc = tr.insertCell();
        var tt = document.createTextNode(data.LastName);
        tc.appendChild(tt);


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
        childDiv2.appendChild(resultTable);
      break;
      default:
    }

    newLI.appendChild(newDiv);
    newDiv.appendChild(childDiv1);
    newDiv.appendChild(childDiv2);
    x.appendChild(newLI);
    $("#index-ol-result").fadeIn("slow");
    var pageturner = document.getElementById("index-d-result-page");
    pageturner.style.display = 'table';
    document.getElementById("index-p-page").textContent = page+1;
}

function pageTurnerFunction(pagechose) {
  $("#index-ol-result").html("");
  if(pagechose == "page-forward"){
    ++page;
    $( document ).ready(function() {
      $.ajax({
          url: "http://"+ipaddress+"/search/allindexes/likegoogle?search="+searchInputValue+"&page="+page,
          contentType: "application/json",
          dataType: 'json',
          success: function(result){
            var number_of_hits = result.totalHits;
            var data = result.hits;
            theFilterViewFunction(data,number_of_hits,page);
            var resultdis = JSON.stringify(result);
            console.log(result);
          }
        })
      });
  }
  if(pagechose == "page-back"){
   if(page > 0){ --page;}
   $( document ).ready(function() {
     $.ajax({
         url: "http://"+ipaddress+"/search/allindexes/likegoogle?search="+searchInputValue+"&page="+page,
         contentType: "application/json",
         dataType: 'json',
         success: function(result){
           var number_of_hits = result.totalHits;
           var data = result.hits;
           theFilterViewFunction(data,number_of_hits,page);
           var resultdis = JSON.stringify(result);
           console.log(result);
         }
       })
     });

  }
}

function openAdvancedSearch(){
  var x = document.getElementById("index-d-advanced");

  if(x.style.display == 'block') x.style.display = 'none';
  else $("#index-d-advanced").fadeIn("slow");
}

function index_save_values(data,value){
  console.log(data);
  if(value ===1 ){
    sessionStorage.setItem("person_id", data);
    sessionStorage.setItem("index_id", "usmgbg");
  }
  else if(value ===0){
    sessionStorage.setItem("person_id", data);
    sessionStorage.setItem("index_id", "larsson_pop");
  }
}
