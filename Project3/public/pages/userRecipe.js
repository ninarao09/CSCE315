/* ***** User Recipe Request ***** */
const userRecipe_app = document.getElementById('root');

const userRecipe_section = document.createElement('div');
userRecipe_section.setAttribute('class', 'user_recipe');
userRecipe_app.appendChild(userRecipe_section);

const userRecipe_container = document.createElement('div');
userRecipe_container.setAttribute('class', 'container');

userRecipe_section.appendChild(userRecipe_container);

const userRecipe_card = document.createElement('div');
const userRecipe_h1 = document.createElement('h1');
const userRecipe_image = document.createElement('img');
const userRecipe_p = document.createElement('p');

function getUserRecipe() {
    var user_keyword = document.getElementById('user_search').value;

    var userRecipe_request = new XMLHttpRequest();
    userRecipe_request.open('GET', `https://api.edamam.com/search?q=${user_keyword}&app_id=${APP_ID}&app_key=${KEY}&from=0&to=1`, true);
    userRecipe_request.onload = function () {
      // Begin accessing JSON data here
      var userRecipe_data = JSON.parse(this.response);
      if (userRecipe_request.status >= 200 && userRecipe_request.status < 400) {
            userRecipe_card.setAttribute('class', 'card');

            userRecipe_h1.textContent = userRecipe_data.hits[0].recipe.label;

            userRecipe_image.setAttribute('class', 'recipeImg');
            userRecipe_image.src = userRecipe_data.hits[0].recipe.image;

            userRecipe_p.textContent = userRecipe_data.hits[0].recipe.url;

            userRecipe_container.appendChild(userRecipe_card);
            userRecipe_card.appendChild(userRecipe_h1);
            userRecipe_card.appendChild(userRecipe_image);
            userRecipe_card.appendChild(userRecipe_p);
      } else {
        const errorMessage = document.createElement('marquee');
        errorMessage.textContent = `Gah, it's not working!`;
        app.appendChild(errorMessage);
      }
    }

    userRecipe_request.send();
}

function removeUserRecipe() {
  userRecipe_container.parentNode.removeChild(userRecipe_container);

  userRecipe_h1.parentNode.removeChild(userRecipe_h1);
  userRecipe_image.parentNode.removeChild(userRecipe_image);
  userRecipe_p.parentNode.removeChild(userRecipe_p);
  userRecipe_card.parentNode.removeChild(userRecipe_card);

  userRecipe_section.appendChild(userRecipe_container);
}
