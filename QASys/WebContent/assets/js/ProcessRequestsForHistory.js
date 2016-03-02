var email;
var xmlobj;
var qas;
var count;

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
	xmlobj.setRequestHeader("type", "history");
	xmlobj.setRequestHeader("email",email);
	xmlobj.send(null);
}

function getUserInformation()
{
	if(xmlobj.readyState==4 && xmlobj.status==200)
	{
		var response = xmlobj.responseText;
		qas = response.split("COMMA");
		count = 0;
		loadQuestions();
	}
	else if(xmlobj.status != 200)
	{
		alert("Server error: " + xmlobj.status);
	}
}

function loadQuestions()
{
	var i = count;
	if(i<qas.length)
	{
		var qa = qas[i];
		var question = qa.split("COLON")[0];
		document.getElementById("q1").innerHTML=question;
		i++;
	}
	else
	{
		document.getElementById("q1").innerHTML="No more question";
		i++;
	}
	
	if(i<qas.length)
	{
		var qa = qas[i];
		var question = qa.split("COLON")[0];
		document.getElementById("q2").innerHTML=question;
		i++;
	}
	else
	{
		document.getElementById("q2").innerHTML="No more question";
		i++;
	}
	
	if(i<qas.length)
	{
		var qa = qas[i];
		var question = qa.split("COLON")[0];
		document.getElementById("q3").innerHTML=question;
		i++;
	}
	else
	{
		document.getElementById("q3").innerHTML="No more question";
		i++;
	}
	
	if(i<qas.length)
	{
		var qa = qas[i];
		var question = qa.split("COLON")[0];
		document.getElementById("q4").innerHTML=question;
		i++;
	}
	else
	{
		document.getElementById("q4").innerHTML="No more question";
		i++;
	}
	
	if(i<qas.length)
	{
		var qa = qas[i];
		var question = qa.split("COLON")[0];
		document.getElementById("q5").innerHTML=question;
		i++;
	}
	else
	{
		document.getElementById("q5").innerHTML="No more question";
		i++;
	}
	
	if(i<qas.length)
	{
		var qa = qas[i];
		var question = qa.split("COLON")[0];
		document.getElementById("q6").innerHTML=question;
		i++;
	}
	else
	{
		document.getElementById("q6").innerHTML="No more question";
		i++;
	}
}

document.getElementById("previous").addEventListener('click',function(event)
{
	event.stopPropagation();
	event.preventDefault();
	
	if((count-6)>=0)
	{
		count = count - 6;
		loadQuestions();
	}
});

document.getElementById("next").addEventListener('click',function(event)
{
	event.stopPropagation();
	event.preventDefault();
		
	if((count+6)<qas.length)
	{
		count = count + 6;
		loadQuestions();
	}
});

document.getElementById("profile").addEventListener('click',function(event)
{
	window.location.assign("profile.html?email=" + email);
});

document.getElementById("index").addEventListener('click',function(event)
{
	window.location.assign("index.html?email=" + email);
});

document.getElementById("aboutus").addEventListener('click',function(event)
{
	window.location.assign("aboutus.html?email=" + email);
});