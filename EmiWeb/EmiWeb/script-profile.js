var id_of_person = sessionStorage.getItem("person_id");
var index_of_person = sessionStorage.getItem("index_id");

$( document ).ready(function() {
            $.ajax({
                url: "http://172.20.10.3:8080/search/specific/"+index_of_person+"_index"+"/byid?Id="+id_of_person,
                contentType: "application/json",
                dataType: 'json',
                success: function(result){
                  var data = result.hits;
                  printProfilePage(data);
                  var resultdis = JSON.stringify(result);
                  console.log(result);
                }
            })
});

function printProfilePage(data){
  var person_info_data = data[0].sourceAsMap;
  document.getElementById("profile-d-content").innerHTML = person_info_data.FirstName;
}
