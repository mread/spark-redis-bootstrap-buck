var welcomeMessage = require('./partials/message.mustache');

$(function() {
    $('#message').html(welcomeMessage());
});
