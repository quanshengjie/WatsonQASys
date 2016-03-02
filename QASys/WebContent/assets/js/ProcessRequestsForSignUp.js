var xmlobj;
var fname;
var mname;
var lname;
var male;
var female;
var notknow;
var email;
var pwd;
var confirmpwd;
var month;
var day;
var year;
var university;
var major;
var gpascale;
var cgpa;
var mgpa;
var program;

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

document.getElementById("signup").addEventListener('click',function(event)
{
	event.stopPropagation();
	event.preventDefault();
	
	fname=document.getElementById("fname");
	mname=document.getElementById("mname");
	lname=document.getElementById("lname");
	male=document.getElementById("male");
	female=document.getElementById("female");
	notknow=document.getElementById("notknow");
	email=document.getElementById("email");
	pwd=document.getElementById("pwd");
	confirmpwd=document.getElementById("confirmpwd");
	month=document.getElementById("month");
	day=document.getElementById("day");
	year=document.getElementById("year");
	university=document.getElementById("university");
	major=document.getElementById("major");
	gpascale=document.getElementById("gpascale");
	cgpa=document.getElementById("cgpa");
	mgpa=document.getElementById("mgpa");
	program=document.getElementById("program");

	if(pwd.value == confirmpwd.value)
	{
		CreateXMLHttpRequest();
		xmlobj.open("POST","../Example2/WebServer",true);
		xmlobj.onreadystatechange=GetFeedback;
		xmlobj.setRequestHeader("type","signup");
		xmlobj.setRequestHeader("fname",fname.value);
		xmlobj.setRequestHeader("mname",mname.value);
		xmlobj.setRequestHeader("lname",lname.value);
		if(male.checked==true)
		{
			xmlobj.setRequestHeader("gender","male");
		}
		else if(female.checked==true)
		{
			xmlobj.setRequestHeader("gender","female");
		}
		else
		{
			xmlobj.setRequestHeader("gender","notknow");
		}
		xmlobj.setRequestHeader("email",email.value);
		xmlobj.setRequestHeader("pwd",pwd.value);
		xmlobj.setRequestHeader("month",month.value);
		xmlobj.setRequestHeader("day",day.value);
		xmlobj.setRequestHeader("year",year.value);
		xmlobj.setRequestHeader("university",university.value);
		xmlobj.setRequestHeader("major",major.value);
		xmlobj.setRequestHeader("gpascale",gpascale.value);
		xmlobj.setRequestHeader("cgpa",cgpa.value);
		xmlobj.setRequestHeader("mgpa",mgpa.value);
		xmlobj.setRequestHeader("program",program.value);
		xmlobj.send(null);
	}
	else
	{
		alert("Please re-confirm password");
	}
});

function GetFeedback()
{
	if(xmlobj.readyState==4 && xmlobj.status==200)
	{
		var response=xmlobj.responseText;
		if(response=="false")
		{
			alert("This email address has been registered!");
		}
		else if(response=="true")
		{
			alert("Success!");
			var url="index.html?email="+email.value;
			window.location.assign(url);
		}
	}
}
