let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
}
function connect() {
    let socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/status', function (status) {
            showResult(status.body);
        });
        stompClient.subscribe('/topic/result', function (status) {
            showResult(status.body);
        });
    });
}



function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    connect()
}

function sendName() {
    stompClient.send("/app/start", {}, JSON.stringify({'name': $("#name").val()}));
}

function showResult(message) {
    let m=JSON.parse(message);
    $( "#genomes_all" ).html(m.genomes_all);
    $( "#genomes_downloaded" ).html(m.genomes_downloaded);
    if (m.genomes_all==0){
        $( "#genomes_downloaded_scale" ).html((0).toFixed(2)+"%");
        $( "#genomes_downloaded_scale" ).attr("style","width: "+0+"%")
        $( "#genomes_ignored" ).html(m.genomes_ignored);
        $( "#genomes_ignored_scale" ).html((0).toFixed(2)+"%");
        $( "#genomes_ignored_scale" ).attr("style","width: "+0+"%")
        $( "#genomes_analysed" ).html(m.genomes_analysed);
        $( "#genomes_analysed_scale" ).html((0).toFixed(2)+"%");
        $( "#genomes_analysed_scale" ).attr("style","width: "+0+"%")
    }else {
        $( "#genomes_downloaded_scale" ).html(((m.genomes_downloaded/m.genomes_all)*100).toFixed(2)+"%");
        $( "#genomes_downloaded_scale" ).attr("style","width: "+(m.genomes_downloaded/m.genomes_all)*100+"%")
        $( "#genomes_ignored" ).html(m.genomes_ignored);
        $( "#genomes_ignored_scale" ).html(((m.genomes_ignored/m.genomes_all)*100).toFixed(2)+"%");
        $( "#genomes_ignored_scale" ).attr("style","width: "+(m.genomes_ignored/m.genomes_all)*100+"%")
        $( "#genomes_analysed" ).html(m.genomes_analysed);
        $( "#genomes_analysed_scale" ).html(((m.genomes_analysed/m.genomes_all)*100).toFixed(2)+"%");
        $( "#genomes_analysed_scale" ).attr("style","width: "+(m.genomes_analysed/m.genomes_all)*100+"%")

    }
    $( "#sequence_restored" ).html(m.sequence_restored);
    $( "#status" ).html(m.genome_status);
}
// $(function () {
//     $("form").on('submit', function (e) {
//         e.preventDefault();
//     });
//     $( "#connect" ).click(function() { connect(); });
//     $( "#disconnect" ).click(function() { disconnect(); });
//     $( "#send" ).click(function() { sendName(); });
// });
function getData(){
    fetch('/status').then(response => response.json()).then(data => showStatus(data))
}
function showStatus(message) {
    let m=(message);
    $( "#genomes_all" ).html(m.genomes_all);
    $( "#genomes_downloaded" ).html(m.genomes_downloaded);
    if (m.genomes_all==0){
        $( "#genomes_downloaded_scale" ).html((0).toFixed(2)+"%");
        $( "#genomes_downloaded_scale" ).attr("style","width: "+0+"%")
        $( "#genomes_ignored" ).html(m.genomes_ignored);
        $( "#genomes_ignored_scale" ).html((0).toFixed(2)+"%");
        $( "#genomes_ignored_scale" ).attr("style","width: "+0+"%")
        $( "#genomes_analysed" ).html(m.genomes_analysed);
        $( "#genomes_analysed_scale" ).html((0).toFixed(2)+"%");
        if(m.unique_sequences!=0){
            $( "#genomes_analysed_scale" ).attr("style","width: "+0+"%")
        }
    }else {
        $( "#genomes_downloaded_scale" ).html(((m.genomes_downloaded/m.genomes_all)*100).toFixed(2)+"%");
        $( "#genomes_downloaded_scale" ).attr("style","width: "+(m.genomes_downloaded/m.genomes_all)*100+"%")
        $( "#genomes_ignored" ).html(m.genomes_ignored);
        $( "#genomes_ignored_scale" ).html(((m.genomes_ignored/m.genomes_all)*100).toFixed(2)+"%");
        $( "#genomes_ignored_scale" ).attr("style","width: "+(m.genomes_ignored/m.genomes_all)*100+"%")
        $( "#genomes_analysed" ).html(m.genomes_analysed);
        $( "#genomes_analysed_scale" ).html(((m.genomes_analysed/m.unique_sequences)*100).toFixed(2)+"%");
        if(m.unique_sequences!=0){
            $( "#genomes_analysed_scale" ).attr("style","width: "+(m.genomes_analysed/m.unique_sequences)*100+"%")
        }
    }

    $( "#sequence_restored" ).html(m.sequence_restored);
    $( "#status" ).html(m.genome_status);
    if(m.genome_status=="DONE"){
       $( "#button" ).attr("style","visibility: visible")
    }
}
setInterval(() => getData(), 500);