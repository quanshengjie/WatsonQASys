var xmlobj;
var email;
var pwd;

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

document.getElementById("login").addEventListener('click',function(event)
{
	event.stopPropagation();
	event.preventDefault();
	
	email=document.getElementById("email");
	pwd=document.getElementById("pwd");

	CreateXMLHttpRequest();
	xmlobj.open("POST","../Example2/WebServer",true);
	xmlobj.onreadystatechange=GetFeedback;
	xmlobj.setRequestHeader("type","login");
	xmlobj.setRequestHeader("email",email.value);
	xmlobj.setRequestHeader("pwd",md5(pwd.value));
	xmlobj.send(null);
});

function GetFeedback()
{
	if(xmlobj.readyState==4 && xmlobj.status==200)
	{
		var response=xmlobj.responseText;
		if(response=="false")
		{
			alert("Wrong Email or Wrong Password!");
		}
		else if(response=="true")
		{
			alert("Success!");
			var url="index.html?email="+email.value;
			window.location.assign(url);
		}
	}
}
