var xmlobj;
var hasUser;
var user;

function Load()
{
	var url=location.href;
	var temp=url.split("?")[1];
	if(temp!=null)
	{
		hasUser=true;
		user=temp.split("=")[1];
	}
	else
	{
		hasUser=false;
	}
	
	if(hasUser==true)
	{
		document.getElementById("login").style.display="none";
		document.getElementById("signup").style.display="none";
		document.getElementById("index").href="index.html?email="+user;
	}
	else
	{
		document.getElementById("login").style.display="inline-block";
		document.getElementById("signup").style.display="inline-block";
	}
}