var tabsQueue = [];
var hostDuration = {};

function postResult(data) {
	var xhr = new XMLHttpRequest();	
    xhr.open("POST", "https://time-leaks-telegram-bot.herokuapp.com/timeleaks", true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    xhr.send(JSON.stringify(data));
}


function processQueue() {
	if(tabsQueue.length < 3) 
		return;
    flushQueue();
}

function flushQueue() {
    if(!tabsQueue || tabsQueue.length < 2)
        return;

    var tabsInfo = tabsQueue.splice(0, tabsQueue.length);
    tabsQueue.push(tabsInfo[tabsInfo.length - 1]);

    for (var i = tabsInfo.length - 1; i >= 1; i--) {
        var currentTab = tabsInfo[i];
        var prevTab = tabsInfo[i - 1];
        var duration = currentTab.time - prevTab.time;
        var host = currentTab.host;
        if(hostDuration[host])
            hostDuration[host] += ( duration / 1000 );
        else
            hostDuration[host] =  ( duration / 1000 );
    }

    tabsInfo.splice(0, tabsInfo.length);

	postResult(hostDuration);
}

function getHost(url) {
	try {
		var u = new URL(url);
		return u.host;
	} catch(err) {
		console.log(err);
		return url;
	}
}

function addToQueueActiveTab() {
	var queryInfo = {
		active: true,
		currentWindow: true
	};
	chrome.tabs.query(queryInfo, function(tabs) {    
		var tab = tabs[0];    
		var url = tab.url;
		var tabInfo = {url: url, host: getHost(url), time: Date.now()};
		tabsQueue.push(tabInfo);
		processQueue();
	});	
}

chrome.tabs.onActivated.addListener(addToQueueActiveTab);
chrome.runtime.onSuspend.addListener(function(){
    if(!tabsQueue || tabsQueue.length === 0)
        return;
    var lastTab = tabsQueue.pop();
    tabsQueue.push(lastTab);
    tabsQueue.push({url: lastTab.url, host: lastTab.host, time: Date.now()});

    tabsQueue.splice(0, tabsQueue.length);
    hostDuration = {};
});