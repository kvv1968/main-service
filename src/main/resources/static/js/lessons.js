let tasksTopic = $('#tasksTopic');
let textOrdinal = $('#textOrdinal');
let ord = document.querySelector('[data-text]');
let userTaskList = $('#user-task-list');

$(function () {
    loadTask();
    loadUserTaskResult();
});

function loadTask() {

    $.ajax({
        url: '/user/nav/' + ord.getAttribute('data-text'),
        method: 'get',
        success: function (data) {
            let li = "";
            let h3;
            let ull;

            for (const [key, values] of Object.entries(data)) {
                h3 = $("<h3 class='mbr-section-title mbr-fonts-style mb-4 display-8'><strong>" + key + "</strong></h3>");
                ull = $("<ol class='list mbr-fonts-style display-7'></ol>");
                for (let i = 0; i < values.length; i++) {
                    li += "<li><a href='/user/nav/lessons/step/" + values[i].id + "'>" + values[i].nameTask + "</a></li>";
                }
                tasksTopic.html(h3);
                ull.html(li);
                tasksTopic.append(ull);
            }

        }
    });
}

function loadUserTaskResult() {
    let ordinal = ord.getAttribute('data-text');
    $.ajax({
        url: '/user/user-tasks/topic/' + ordinal,
        method: 'get',
        success: function (data) {
            let i = 1;
            for (const [key, values] of Object.entries(data)) {
                let tr = $("<tr id='pow" + i + "'></tr>");
                let td = "";
                td += "<td id='td-" + i + "'>" + i + "</td>";
                td += "<td id='td-" + i + "-key'>" + key.valueOf() + "</td>";
                if (values){
                    td += "<td id='td-" + i + "-values' style='color: #20c997'>ПРОЙДЕН</td>";
                }else {
                    td += "<td id='td-" + i + "-values' style='color: #bb2d3b'>НЕ ПРОЙДЕН</td>";
                }


                tr.html(td);
                userTaskList.append(tr);
                i++;
            }

        }
    });
}