<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
		.errorblock {
			color: #ff0000;
			padding: 8px;
			margin: 16px;
		}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>!!! Insert title here</title>
</head>
<body>
	<script type="text/javascript" src="jquery-1.8.3.js"></script>
 
 	<script type="text/javascript">
 	  var lastActivityId = 0;
	  var xmlhttp;
	  
	  function processJsonActivityList( data ) {
			var len = data.length;
			for (var i = 0; i < len; i++) {
				
				lastActivityId = data[i].id;
				
				var html = '<div>';
				html = html + '<table>';
				html = html + '<tr>'; 
				//html = html + '<td colspan="2"><p>' + data[i].text  +  '</p></td>';
				html = html + '<td colspan="2"><textarea disabled>' + data[i].text  +  '</textarea></td>';
				html = html + '<td><p>' + '' +  '</p></td>';
				//html = html + '<td><p>' + data[i].id  +  '</p></td>';
				html = html + '</tr>'; 
				
				var user = data[i].user;
				
				html = html + '<tr>'; 
				html = html + '<td><p>' + ''  +  '</p></td>';
				html = html + '<td><p>' + user.username  +  '</p></td>';
				html = html + '</tr>'; 

				html = html + '</table>';
				html = html + '</div>';

		    	$("#text").prepend(html);
			}
	  }
	  
	  function updateActivities() {
		  var groupId = $('#groups').val();
		  if( groupId == '' || groupId== null) {
			  return;
		  }
		  
	     xmlhttp = new XMLHttpRequest();
	     xmlhttp.onreadystatechange = function() {
	     if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
		     var json = JSON.parse( xmlhttp.responseText );
	    	 processJsonActivityList(json);
	      }

	    };
	    
	    //var uri = "http://localhost:8080/ActivityStreamExample/updateActivities.json" + "?" + "id=" + lastActivityId ;
	    var uri = "updateActivities.json" + "?" + "id=" + lastActivityId + "&" + "groupid=" + groupId;
	    xmlhttp.open("GET", uri , true);
	    xmlhttp.send();
	  }
	  
	  $(document).ready(function(){
	
		  //setInterval(function(){
			//  updateActivities();
			//}, 30000);

		  getGroups();
		  updateActivities();
	  });

	  
	  function update() {
		  updateActivities();
	  }

	  function onChangeGroup() {
		  var selected = $('#groups').val();
		  if( selected != '' ) {
			  $("#selectGroupsError").html('');
	  	  }
		  
		  $("#text").html('');
	 	  lastActivityId = 0;
		  
		  updateActivities();
	  }
	  
	  function addActivity() {
		  var groupId = $('#groups').val();
		  if( groupId == '' || groupId == null ) {
			  $("#selectGroupsError").html('Activities must be selected');
			  return;
		  }
          var activityText = $('#inputAction').val();
		  var activityFeed = {"activityText":activityText, "groupId":groupId }
	    	var dataToSendSTR = JSON.stringify(activityFeed);
	    	$.ajax ({
	    	    url: "addNewActivity.json",
	    	    type: "POST",
	    	    data: dataToSendSTR,
	    	    dataType: "json",
	    	    contentType: "application/json; charset=utf-8",
	    	    success: function(response){
  	   	    	    processJsonActivityList(response);
  	   	    		$('#inputAction').val('')
	    	    },
	    	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	    	if( XMLHttpRequest.status == 400 )
	    	        	alert("Activity text must be from 3 to 200 charactes");
	    	     }
	    	});
		  
	  }
	  
	  function getGroups() {
	    	$.ajax ({
	    	    url: "getGroupList.json",
	    	    type: "GET",
	    	    success: function(data){
					var html = '<option value="">--Please select group--</option>';
					var len = data.length;
					for (var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';
					$('#groups').html(html);
	    	    }
	    	});
	  }

	</script>

	<div id="header">
		<table>
			<tr>
				<td>
		             <p>User:</p>			
				</td>
				<td>
				     <p>${userName}</p>
				</td>
				<td>
				      <a href="logout.html">Logout</a>
				</td>
			</tr>
		</table>
	</div>
 
	<div id="inputActivities">
		<div>
		<!-- 
			<form action="postActivity.html" method="post">
 		-->
				<table>
					<tr>
						<td colspan="3">
							<span id="selectGroupsError" class="errorblock"></span>
						</td>
					</tr>
					<tr>
          				<td>
	          				<label for="selectinput1">Available Groups</label>
          				</td>
          				<td>
          						<select id="groups" onchange="onChangeGroup()">
          						</select>
          						
          				</td>
          				<td>
          						<a href="groupsPage.html">Edit Groups</a>
          						
          				</td>
					</tr>
    				<tr>
        				<td><label for="inputAction">Action Description</label></td>
        				<td colspan="2"><textarea id="inputAction" name="inputAction"></textarea>
        				</td>
    				</tr>
    				<tr>
        				<td colspan="3">
        				<!-- 
            				<input type="submit" value="Save" onclick="update()" />
        				 -->
            				<button type="button" onclick="addActivity()">Add Activity</button>
        				</td>
    				</tr>
				</table>
		<!--   
			</form>
		 -->
		</div>
	</div>
		<hr>
	<div id="listActivities">
		<h3>List Activities</h3>
		<div id="text">
				<p>No Activities</p>
		</div>
	</div>



</body>
</html>
