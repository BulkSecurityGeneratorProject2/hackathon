function OnLoginClick() {
	console.log(document.getElementById("regform").style.display);
	if(document.getElementById("loginbutton").innerHTML != "Войти") return;
    if(document.getElementById("regform").style.display == "none") { document.getElementById("regform").style.display = "block"; }
	else { document.getElementById("regform").style.display = "none"; }
}

function OnLoginConfirm() {
	console.log(document.getElementById("login").value);
	console.log(document.getElementById("password").value);
	
	fetch('http://localhost:8080/api/users/' + document.getElementById("login").value)
					.then(response => response.json())
					.then(data => {		
						console.log(data);
						userJSON = data;
						if(document.getElementById("password").value == data.password && document.getElementById("login").value == data.login){
							console.log("success");
							document.getElementById("loginbutton").innerHTML = "Настройки";
							document.getElementById("loginbutton").href = "./user.html";
							document.getElementById("regform").style.display = "none";
							document.getElementById("username").innerHTML = data.firstName + " " + data.lastName;
							document.getElementById("username").style.display = "block";
							document.getElementById("avatar").style.display = "block";
						}
					});
					
	console.log("some js happened");
}