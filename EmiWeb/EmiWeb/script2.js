/*THE SCRIPT FILE FOR INDEX.HTML*/
function showListFunction(click_id) {
  console.log(click_id);
  var x = document.getElementById(click_id);
  console.log(x.style.display);
  if (x.style.display != "block") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

window.onload = function()
{
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

function allSearchFunction(){
  console.log("in all search");
  var x = document.getElementById("googleSearch");
  console.log(x);
}


function googleSearchFunction(){
  var search = document.getElementById("googleSearch").value;
  console.log(search);
  $.ajax({
      url: "http://192.168.137.249:8080/allindexes/"+search,
      contentType: "application/json",
      dataType: 'json',
      success: function(result){
          console.log(result);
          alert(result);
          console.log(result.hits.hits[0].sourceAsMap.FirstName);
      }
  })
}

function openAdvancedSearch(){
  var x = document.getElementById("advDiv");
  x.style.display = 'block'; 
}
