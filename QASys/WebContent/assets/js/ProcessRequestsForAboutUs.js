var xmlobj;
var hasUser;
var email;

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
		document.getElementById("index").href="index.html?email="+email;
		document.getElementById("usernav").style.display="block";
		document.getElementById("username").innerHTML=email;
	}
	else
	{
		document.getElementById("login").style.display="inline-block";
		document.getElementById("signup").style.display="inline-block";
		document.getElementById("index").href="index.html";
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

document.getElementById("history").onclick = function(event)
{
	window.location.assign("history.html?email=" + email);
	return false;
};