/*comment jscript*/

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
