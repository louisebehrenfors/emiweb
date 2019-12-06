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

function openHelp(helpName) {
  var i;
  var x = document.getElementsByClassName("helpSection");
  for (i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  document.getElementById(helpName).style.display = "block";
  
}
