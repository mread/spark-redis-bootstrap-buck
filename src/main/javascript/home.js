var welcomeMessage = require('./partials/message.mustache');

$(function () {
  $('#message1').text('Javascript is enabled');
  $('#message2').html(welcomeMessage({greeting: 'Hello'}));
});
