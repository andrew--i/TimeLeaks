var timeleaks = [];

function all () {
    return timeleaks;
}

function save(item) {
    timeleaks.push(item);
}

module.exports = {
    all:all,
    save:save
}