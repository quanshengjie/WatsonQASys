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
		var userInformation = response.split("END")[0];
		var qas = response.split("END")[1].split(",");
		var i = 0;
		for (i = 0; i < qas.length; i++)
		{
			var qa = qas[i];
			var question = qa.split(":")[0];
			var answer = qa.split(":")[1];
		}
	}
	else if(xmlobj.status != 200)
	{
		alert("Server error: " + xmlobj.status);
	}
}