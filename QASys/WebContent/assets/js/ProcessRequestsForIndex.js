var xmlobj;
var question;
var answer;
var hasUser;
var user;

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

function GetAnswer()
{
	if(xmlobj.readyState==4 && xmlobj.status==200)
	{
		answer.value=xmlobj.responseText;
	}
}

document.getElementById("ask").addEventListener('click',function(event)
{
	event.stopPropagation();
	event.preventDefault();
	
	question=document.getElementById("question");
	answer=document.getElementById("answer");

	CreateXMLHttpRequest();
	xmlobj.open("POST","../Example2/WebServer",true);
	xmlobj.onreadystatechange=GetAnswer;
	xmlobj.setRequestHeader("type","ask");
	xmlobj.setRequestHeader("question",question.value);
	xmlobj.send(null);
});

function Load()
{
	var url=location.href;
	var temp=url.split("?")[1];
	if(temp!=null)
	{
		hasUser=true;
	}
	else
	{
		hasUser=false;
		user=temp1.split("=")[1];
	}
	
	if(hasUser==true)
	{
		document.getElementById("login").style.display="none";
		document.getElementById("signup").style.display="none";
	}
	else
	{
		document.getElementById("login").style.display="inline-block";
		document.getElementById("signup").style.display="inline-block";
	}
}