<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
   .hidden {
        display: none;
   {
   .visible {
        display: block;
   }
   

</style>
<style>
   th, td  {
    border: 1px solid black;
   }
</style>
<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript" src="jquery-1.8.3.js"></script>
    <script src="assets/js/bootstrap.js">
    </script>

    <script type="text/javascript"> 
 	

    var GroupActionEnum = {"create":0, "update":1, "remove":2};
    
    function sentJsonPostRequest(jsonData) {

    	var dataToSendSTR = JSON.stringify(jsonData);
    	$.ajax ({
    	    url: "changeGroup.json",
    	    type: "POST",
    	    data: dataToSendSTR,
    	    dataType: "json",
    	    contentType: "application/json; charset=utf-8",
    	    success: function(){
    	    	getAllGroups();
    	    }
    	});
    	
    }
    
    function addNewGroup( ) {
    	var grpName = $('#groupName').val();
    	var grpId = null;
    	var request = {"id": grpId, "name":grpName, action:GroupActionEnum.create  };
    	sentJsonPostRequest( request );

    	$('#groupName').val(null);
 	    $('#groupId').val(null);

    }

    function updateGroup( ) {
    	var grpName = $('#groupName').val();
    	var grpId = $('#groupId').val();
    	var request = {"id": grpId, "name":grpName, action:GroupActionEnum.update  };
    	sentJsonPostRequest( request );
    	
    	cancelEdit();
    }

    
    function deleteGroup(id ) {
    	var grpName = null;
    	var grpId = id;
    	var request = {"id": grpId, "name":grpName, action:GroupActionEnum.remove };
    	sentJsonPostRequest( request );
    }
 	
    function opentEditGroupPage(str, id) {
	    	var btnNewGroup = document.getElementById('buttonNewGroup');
	    	btnNewGroup.setAttribute('class', 'hidden');
 	    	
	    	var btnCancelEdit = document.getElementById('buttonCancelEdit');
	    	btnCancelEdit.setAttribute('class', 'visible');

	    	var btnSaveEdit = document.getElementById('buttonSaveEdit');
	    	btnSaveEdit.setAttribute('class', 'visible');

 	    	$('#groupName').val(str);
 	    	$('#groupId').val(id);
 	    	 
 	    }

 	
    function cancelEdit() {
    	
    	var btnNewGroup = document.getElementById('buttonNewGroup');
    	btnNewGroup.setAttribute('class', 'visible');
	    	
    	var btnCancelEdit = document.getElementById('buttonCancelEdit');
    	btnCancelEdit.setAttribute('class', 'hidden');

    	var btnSaveEdit = document.getElementById('buttonSaveEdit');
    	btnSaveEdit.setAttribute('class', 'hidden');

    	$('#groupName').val(null);
 	    $('#groupId').val(null);

    }

 	function displayAllGroups( data ) {
    	var html = '<tr>' + '<th>' + 'Group'  + '</th>' + '<th>' + 'Edit'  + '</th>' + '<th>' + 'Delete'  + '</th>' + '</tr>' ;
		var len = data.length;
		if ( len > 0 ) {
			for (var i = 0; i < len; i++) {
				
				html = html + '<tr>';
	            html = html + '<td>' + data[i].name + '</td>'; 
	            html = html + '<td>' + '<button onclick="opentEditGroupPage(' + '\'' + data[i].name + '\'' + ',' + data[i].id  + ')"' + '>Edit</button>' + '</td>'; 
	            html = html + '<td>' + '<button onclick="deleteGroup(' + data[i].id + ')"' + '>Delete</button>' + '</td>'; 
		    	html = html + '</tr>';
			}
		} else {
			html = html + '<tr>';
			html = html + '<td colspan="3">' + 'No groups' + '</td>';
	    	html = html + '</tr>';
						
		}
		$('#group_list').html(html);
 	}
 	
    function getAllGroups() {
 			$.getJSON('<spring:url value="getGroupList.json"/>', {
 				ajax : 'true'
 			}, function(jsonData){
 				displayAllGroups( jsonData );
         	});
    }
        
 	
    $(document).ready(function(){
 			getAllGroups();
    });

 	</script>
	
	<div id="header">
		<a href="mainPage.html">Back to Activities</a>
	</div>
	<br/>
	<div id="editDivAttr" class="visible" >
	<!-- 
	<form id="editGroupForm" action="addNewGroup.html" method="post">
	 -->
  			First name: <input id="groupName" type="text" name="groupName"> 
  			<input id="groupId" class="hidden" type="text" name="groupId"><br>
  			<br>

<!--   			
  			<input id="buttonNewGroup" class="visible" type="submit" value="Submit">
 -->
  			<button id="buttonNewGroup" type="button" class="visible" onclick="addNewGroup()">Add New Group</button>
  			<button id="buttonCancelEdit"  type="button" class="hidden" onclick="cancelEdit()">Cancel Edit</button>
  			<button id="buttonSaveEdit"  type="button" class="hidden"  onclick="updateGroup()">Update Group</button>
  		<!-- 	
		</form>
		 -->
	</div>
	
	<br/>
	<br/>
	
	<table id="group_list">
	</table>
</body>
</html>