/* ***** Quick Recipe Request ***** */
const app = document.getElementById('root');

const section = document.createElement('div');
section.setAttribute('class', 'quick_easy');
app.appendChild(section);

const container = document.createElement('div');
container.setAttribute('class', 'container');

section.appendChild(container);

var request = new XMLHttpRequest();
request.open('GET', `https://api.edamam.com/search?q=quick&app_id=${APP_ID}&app_key=${KEY}&from=0&to=1`, true);
request.onload = function () {
  // Begin accessing JSON data here
  var data = JSON.parse(this.response);
  if (request.status >= 200 && request.status < 400) {
        const card = document.createElement('div');
        card.setAttribute('class', 'card');

        const h1 = document.createElement('h1');
        h1.textContent = data.hits[0].recipe.label;

        const image = document.createElement('img');
        image.setAttribute('class', 'recipeImg');
        image.src = data.hits[0].recipe.image;

        const p = document.createElement('p');
        p.textContent = data.hits[0].recipe.url;
        //p.textContent = "..."
        //document.getElementById('recipeName').innerHTML = p.textContent;

        container.appendChild(card);
        card.appendChild(h1);
        card.appendChild(image);
        card.appendChild(p);
        //recipeName = JSON.stringify(data.hits[0].recipe.url);
  } else {
    const errorMessage = document.createElement('marquee');
    errorMessage.textContent = `Error while accessing API...`;
    app.appendChild(errorMessage);
  }
}

request.send();

/* ***** Low Carb Request ***** */
const lowC_section = document.createElement('div');
lowC_section.setAttribute('class', 'low_carb');
app.appendChild(lowC_section);

const lowC_container = document.createElement('div');
lowC_container.setAttribute('class', 'container');

lowC_section.appendChild(lowC_container);

var lowC_request = new XMLHttpRequest();
lowC_request.open('GET', `https://api.edamam.com/search?q=keto&app_id=${APP_ID}&app_key=${KEY}&from=0&to=1`, true);
lowC_request.onload = function () {
  // Begin accessing JSON data here
  var lowC_data = JSON.parse(this.response);
  if (lowC_request.status >= 200 && lowC_request.status < 400) {
        const lowC_card = document.createElement('div');
        lowC_card.setAttribute('class', 'card');

        const lowC_h1 = document.createElement('h1');
        lowC_h1.textContent = lowC_data.hits[0].recipe.label;

        const lowC_image = document.createElement('img');
        lowC_image.setAttribute('class', 'recipeImg');
        lowC_image.src = lowC_data.hits[0].recipe.image;

        const lowC_p = document.createElement('p');
        lowC_p.textContent = lowC_data.hits[0].recipe.url;
        //p.textContent = "..."
        //document.getElementById('recipeName').innerHTML = p.textContent;

        lowC_container.appendChild(lowC_card);
        lowC_card.appendChild(lowC_h1);
        lowC_card.appendChild(lowC_image);
        lowC_card.appendChild(lowC_p);
        //recipeName = JSON.stringify(data.hits[0].recipe.url);
  } else {
    const errorMessage = document.createElement('marquee');
    errorMessage.textContent = `Error while accessing API...`;
    app.appendChild(errorMessage);
  }
}

lowC_request.send();

/* ***** High Protein Request ***** */
const highP_section = document.createElement('div');
highP_section.setAttribute('class', 'high_protein');
app.appendChild(highP_section);

const highP_container = document.createElement('div');
highP_container.setAttribute('class', 'container');

highP_section.appendChild(highP_container);

var highP_request = new XMLHttpRequest();
highP_request.open('GET', `https://api.edamam.com/search?q=protein&app_id=${APP_ID}&app_key=${KEY}&from=0&to=1`, true);
highP_request.onload = function () {
  // Begin accessing JSON data here
  var highP_data = JSON.parse(this.response);
  if (highP_request.status >= 200 && highP_request.status < 400) {
        const highP_card = document.createElement('div');
        highP_card.setAttribute('class', 'card');

        const highP_h1 = document.createElement('h1');
        highP_h1.textContent = highP_data.hits[0].recipe.label;

        const highP_image = document.createElement('img');
        highP_image.setAttribute('class', 'recipeImg');
        highP_image.src = highP_data.hits[0].recipe.image;

        const highP_p = document.createElement('p');
        highP_p.textContent = highP_data.hits[0].recipe.url;
        //p.textContent = "..."
        //document.getElementById('recipeName').innerHTML = p.textContent;

        highP_container.appendChild(highP_card);
        highP_card.appendChild(highP_h1);
        highP_card.appendChild(highP_image);
        highP_card.appendChild(highP_p);
        //recipeName = JSON.stringify(data.hits[0].recipe.url);
  } else {
    const errorMessage = document.createElement('marquee');
    errorMessage.textContent = `Error while accessing API...`;
    app.appendChild(errorMessage);
  }
}

highP_request.send();
