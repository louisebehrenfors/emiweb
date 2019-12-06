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

  var mybutton = document.getElementById("myBtn");

  if (document.body.scrollTop > 30|| document.documentElement.scrollTop > 30) {
    mybutton.style.display = 'block';
  } else {
    mybutton.style.display = 'none';
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
