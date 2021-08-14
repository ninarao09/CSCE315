/*
//document.getElementById('test').onclick = function(){

	const data = null;

	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;

	xhr.addEventListener("readystatechange", function () {
		if (this.readyState === this.DONE) {
			const data = JSON.parse(xhr.response);
			console.log(data);
			console.log(data.author);
			console.log(data.text);

			document.getElementById("API2").innerHTML = data.text;
			document.getElementById("API").innerHTML = "- " + data.author;



		}
	});

	xhr.open("GET", "https://quotes-inspirational-quotes-motivational-quotes.p.rapidapi.com/quote?token=ipworld.info");
	xhr.setRequestHeader("x-rapidapi-key", "a80fed8943mshe94231f48da4123p1ba7e1jsn323cd854252e");
	xhr.setRequestHeader("x-rapidapi-host", "quotes-inspirational-quotes-motivational-quotes.p.rapidapi.com");

	xhr.send(data);
//};
//};*/

const data = {
	"key1": "value",
	"key2": "value"
};

const xhr = new XMLHttpRequest();
xhr.withCredentials = true;


xhr.addEventListener("readystatechange", function () {
	if (this.readyState === this.DONE) {

		console.log(xhr.response);

	
		document.getElementById("API2").innerHTML = "Motivational Quote: " + "<br>"+ xhr.response;
	}
});

xhr.open("POST", "https://motivational-quotes1.p.rapidapi.com/motivation");
xhr.setRequestHeader("content-type", "application/json");
xhr.setRequestHeader("x-rapidapi-key", "a80fed8943mshe94231f48da4123p1ba7e1jsn323cd854252e");
xhr.setRequestHeader("x-rapidapi-host", "motivational-quotes1.p.rapidapi.com");

xhr.send();
