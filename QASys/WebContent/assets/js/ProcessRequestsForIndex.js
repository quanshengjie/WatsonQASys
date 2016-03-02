var xmlobj;
var question;
var hasUser;
var email;

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
		stopProgressBar();
		var answer = document.getElementById("main_answer_div")
		answer.innerHTML = xmlobj.responseText;
		var contentArea = document.getElementById("content_area");
		var rect = contentArea.getBoundingClientRect();
		var contentHeight = window.innerHeight - rect.top - 80;
		var actualHeight = $('#main_answer_div').height();
		if(actualHeight > contentHeight)
		{
			scrollToInputFormTop();
		}
	}
	else if(xmlobj.status != 200)
	{
		alert("Server error: " + xmlobj.status);
	}
}

document.getElementById("ask").addEventListener('click',function(event)
{
	event.stopPropagation();
	event.preventDefault();
	question=document.getElementById("question");
	var temp = question.value.trim();
	if(temp != "")
	{
		var answer = document.getElementById("main_answer_div")
		answer.innerHTML = "";
		startProgressBar();

		CreateXMLHttpRequest();
		xmlobj.open("POST","../Example2/WebServer",true);
		xmlobj.onreadystatechange=GetAnswer;
		xmlobj.setRequestHeader("type","ask");
		xmlobj.setRequestHeader("question",question.value.trim());
		if(hasUser)
		{	
			xmlobj.setRequestHeader("hasUser", "true");
			xmlobj.setRequestHeader("email", email);
		}
		else
		{
			xmlobj.setRequestHeader("hasUser", "false");
		}
		xmlobj.send(null);
	}
});

function Load()
{
	var url=location.href;
	var temp=url.split("?")[1];
	if(temp!=null)
	{
		hasUser=true;
		email=temp.split("=")[1];
	}
	else
	{
		hasUser=false;
	}
	
	if(hasUser==true)
	{
		document.getElementById("login").style.display="none";
		document.getElementById("signup").style.display="none";
		document.getElementById("aboutus").href="aboutus.html?email="+email;
		document.getElementById("usernav").style.display="block";
		document.getElementById("username").innerHTML=email;
	}
	else
	{
		document.getElementById("login").style.display="inline-block";
		document.getElementById("signup").style.display="inline-block";
		document.getElementById("aboutus").href="aboutus.html";
		document.getElementById("usernav").style.display="none";
	}
}

document.getElementById("logout").onclick = function(event)
{
	hasUser=false;
	window.location.assign("index.html");
	return false;
};

document.getElementById("profile").onclick = function(event)
{
	window.location.assign("profile.html?email=" + email);
	return false;
};

document.getElementById("history").onclick = function()
{
	window.location.assign("history.html?email=" + email);
	return false;
};