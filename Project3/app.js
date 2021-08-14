const APP_ID = "11fce68e"
const KEY = "6225686cdbf5eb856e4e6daa669e22ec"
const URL = `https://api.edamam.com/search?q=keto&app_id=${APP_ID}&app_key=${KEY}&from=0&to=1`

const axios = require("axios").default;
const request = require("request")

request(URL, (error, response, body) => {
    const data = JSON.parse(body)
    //console.log(data.hits[0])
    console.log(data.hits[0].recipe.url)
    console.log(data.hits[0].recipe.label)
    console.log(data.hits[0].recipe.image)
    console.log(data.hits[0].recipe.source)
})
