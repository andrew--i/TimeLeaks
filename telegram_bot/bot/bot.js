const TelegramBot = require('node-telegram-bot-api');
const token = process.env.BOT_TOKEN;
const bot = new TelegramBot(token, {polling: true});


function isValidMessage(message) {
    var userName = process.env.TELEGRAM_USERNAME;
    return message.from.username === userName;
}


module.exports = function (repository) {
    bot.on('message', function (msg) {
        const chatId = msg.chat.id;
        if (isValidMessage(msg)) {
            bot.sendMessage(chatId, JSON.stringify(repository.all()));
        }
    });

}