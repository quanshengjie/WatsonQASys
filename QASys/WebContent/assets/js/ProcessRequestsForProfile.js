var email;
var xmlobj;

function CreateXMLHttpRequest()
{
	if(window.ActiveXObject)
	{
		xmlobj=new ActiveXObject("Microsoft.XMLHTTP");
	}
	else
	{
		xmlobj=new XMLHttpRequest();
	}
}

function Load()
{
	var url = location.href;
	var temp = url.split("?")[1];
	email = temp.split("=")[1];

	CreateXMLHttpRequest();
	xmlobj.open("POST","../Example2/WebServer",true);
	xmlobj.onreadystatechange=getUserInformation;
	xmlobj.setRequestHeader("type", "profile");
	xmlobj.setRequestHeader("email",email);
	xmlobj.send(null);
}

function getUserInformation()
{
	if(xmlobj.readyState==4 && xmlobj.status==200)
	{
		var response = xmlobj.responseText;
		var userInformation = response.split("COMMA");
		var fname=userInformation[0].split("COLON")[1];
		var mname=userInformation[1].split("COLON")[1];
		var lname=userInformation[2].split("COLON")[1];
		var month=userInformation[3].split("COLON")[1];
		var day=userInformation[4].split("COLON")[1];
		var year=userInformation[5].split("COLON")[1];
		var university=userInformation[6].split("COLON")[1];
		var major=userInformation[7].split("COLON")[1];
		var gpascale=userInformation[8].split("COLON")[1];
		var cgpa=userInformation[9].split("COLON")[1];
		var mgpa=userInformation[10].split("COLON")[1];
		var program=userInformation[11].split("COLON")[1];
		
		document.getElementById("name").value = fname + " " + mname + " " + lname;
		document.getElementById("email").value = email;
		document.getElementById("birthday").value = month+"/"+day+"/"+year;
		document.getElementById("university").value = university;
		document.getElementById("major").value = major;
		document.getElementById("gpascale").value = gpascale;
		document.getElementById("cgpa").value = cgpa;
		document.getElementById("mgpa").value = mgpa;
		document.getElementById("program").value = program;
	}
	else if(xmlobj.status != 200)
	{
		alert("Server error: " + xmlobj.status);
	}
}

document.getElementById("submit").addEventListener('click',function(event)
{
	event.stopPropagation();
	event.preventDefault();
					
	var newpwd = document.getElementById("newpwd").value;
	var newconfirmpwd = document.getElementById("newconfirmpwd").value;
	if(newpwd == newconfirmpwd)
	{
		CreateXMLHttpRequest();
		xmlobj.open("POST","../Example2/WebServer",true);
		xmlobj.setRequestHeader("type", "changepwd");
		xmlobj.setRequestHeader("email",email);
		xmlobj.setRequestHeader("pwd",newpwd);
		xmlobj.send(null);
		
		document.getElementById("newpwd").value = "";
		document.getElementById("newconfirmpwd").value = "";
	}
	else
	{
		alert("Please re-confirm your new password");
	}
});

document.getElementById("history").addEventListener('click',function(event)
{
	window.location.assign("history.html?email=" + email);
});

document.getElementById("index").addEventListener('click',function(event)
{
	window.location.assign("index.html?email=" + email);
});

document.getElementById("aboutus").addEventListener('click',function(event)
{
	window.location.assign("aboutus.html?email=" + email);
});