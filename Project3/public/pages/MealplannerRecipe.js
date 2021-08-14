/* ***** User Recipe Request ***** */


function getUserRecipe() {
  const APP_ID = "ab0bacf8"
  const KEY = "fcc640859cf13c9cd3c43cc175c81026"

  const userRecipe_section = document.createElement('div');
  userRecipe_section.setAttribute('class', 'user_recipe');

  var user_keyword = document.getElementById('user_searchb1').value;
  var userRecipe_app = document.getElementById('b1');
  if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchb2').value;
    userRecipe_app = document.getElementById('b2');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchb3').value;
    userRecipe_app = document.getElementById('b3');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchb4').value;
    userRecipe_app = document.getElementById('b4');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchb5').value;
    userRecipe_app = document.getElementById('b5');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchb6').value;
    userRecipe_app = document.getElementById('b6');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchb7').value;
    userRecipe_app = document.getElementById('b7');
  }
  if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchl1').value;
    userRecipe_app = document.getElementById('l1');
  }if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchl2').value;
    userRecipe_app = document.getElementById('l2');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchl3').value;
    userRecipe_app = document.getElementById('l3');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchl4').value;
    userRecipe_app = document.getElementById('l4');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchl5').value;
    userRecipe_app = document.getElementById('l5');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchl6').value;
    userRecipe_app = document.getElementById('l6');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchl7').value;
    userRecipe_app = document.getElementById('l7');
  }
  if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchd1').value;
    userRecipe_app = document.getElementById('d1');
  }if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchd2').value;
    userRecipe_app = document.getElementById('d2');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchd3').value;
    userRecipe_app = document.getElementById('d3');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchd4').value;
    userRecipe_app = document.getElementById('d4');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchd5').value;
    userRecipe_app = document.getElementById('d5');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchd6').value;
    userRecipe_app = document.getElementById('d6');
  } if(user_keyword == ''){
    user_keyword = document.getElementById('user_searchd7').value;
    userRecipe_app = document.getElementById('d7');
  }

  userRecipe_app.appendChild(userRecipe_section);

  const userRecipe_container = document.createElement('div');
  userRecipe_container.setAttribute('class', 'container');
  userRecipe_section.appendChild(userRecipe_container);

        
    

    var userRecipe_request = new XMLHttpRequest();
    userRecipe_request.open('GET', `https://api.edamam.com/search?q=${user_keyword}&app_id=${APP_ID}&app_key=${KEY}&from=0&to=1`, true);
    userRecipe_request.onload = function () {
      // Begin accessing JSON data here
      var userRecipe_data = JSON.parse(this.response);
      if (userRecipe_request.status >= 200 && userRecipe_request.status < 400) {
            const userRecipe_card = document.createElement('div');
            userRecipe_card.setAttribute('class', 'card');

            const userRecipe_h3 = document.createElement('h3');
            userRecipe_h3.textContent = userRecipe_data.hits[0].recipe.label;
            const userRecipe_p = document.createElement('p');
            userRecipe_p.textContent = userRecipe_data.hits[0].recipe.url;
            userRecipe_container.appendChild(userRecipe_h3);

            userRecipe_container.appendChild(userRecipe_p);
      } else {
        const errorMessage = document.createElement('marquee');
        errorMessage.textContent = `Gah, it's not working!`;
        app.appendChild(errorMessage);
      }
    }

    userRecipe_request.send();
}