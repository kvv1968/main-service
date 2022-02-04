let task = $('#taskTemplate');
let formTask = $('#formTask');
let answerUser = $('#answerUser');
let userTaskListResult = $('#user-task-list-result');
let ord = document.querySelector('[data-step]');
let strongText = $('#strong-text');
let editor;

$(function () {
    loadStep();
    loadUserTaskStory();
});


function loadStep() {
    let step = ord.getAttribute('data-step');
    $.ajax({
        url: '/user/nav/step/' + step,
        method: 'get',
        success: function (data) {
            loadTask(data);
        }
    });
}

function loadUserTaskStory() {
    let step = ord.getAttribute('data-step');
    $.ajax({
        url: '/user/task-story/' + step,
        method: 'get',
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                let userTask = data[i];
                let tr = $("<tr id='tr-" + i + "'></tr>");
                let td = "";
                td += "<td id='td-" + i + "'>" + (i+1) +"</td>";
                td += "<td id='td-" + i +"-message'>" + userTask.message + "</td>";
                if (userTask.isResultTask){
                    td += "<td id='td-" + i +"-result' style='color: #20c997'>РЕШЕНИЕ ПРАВИЛЬНОЕ</td>";
                }else {
                    td += "<td id='td-" + i +"-result' STYLE='color: #bb2d3b'>РЕШЕНИЕ НЕ ПРАВИЛЬНОЕ</td>";
                }

                tr.html(td);
                userTaskListResult.append(tr);

            }
        }
    });
}

function loadTask(data) {
    if (data.isQuestion) {
        createQuestion(data);
    } else {
        createExercise(data);
    }
}

function createExercise(data) {
    $('#h-text').text(data.nameTask);
    strongText.text(data.description);
    $('#idTask').val(data.id);
    let textarea = "<textarea class='form-control' id='code' name='answer'></textarea>";

    task.append(textarea);
    editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        // mode: "text/groovy",
        mode: "text/x-java",
        lineNumbers: true,
        theme: "dracula",
        lineWrapping: true,
        foldGutter: true,
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
        matchBrackets: true,
        //readOnly: true,
    });
    editor.setSize('800px', '500px');
    editor.setValue(data.res);
}

function createQuestion(data) {
    let nameTask = data.nameTask;
    $('#h-text').text(nameTask);
    strongText.text(data.description);
    $('#idTask').val(data.id);
    let arr = data.res.split('#');
    let inp = "";
    if (nameTask.indexOf('ЭКЗАМЕН') !== -1){
        inp += "<ol>";
        for (let i = 0; i < arr.length; i++) {
           inp += "<li>" + arr[i] + "</li>";
        }
        inp += "</ol><br><br><p><a href='/user/exam-date'>Согласовать дату митапа</a></p>";
        $("#buttonForm").hide();
    } else {
        if (!isEmpty(data.path)){
            let img = "<br><img src='../static/images/" + data.path + "' th:src='@{/images/"+data.path+"}' alt='Girl in a jacket'>";
            strongText.append(img);
        }
        shuffle(arr);
        for (let i = 0; i < arr.length; i++) {
            inp += "<p><input type='checkbox' name='answer' value='" + arr[i] + "' id='answer"+i+"'>" + arr[i] + "</p>";
        }
    }
    task.append(inp);
}

let code = $('#code');


formTask.on('submit', function (event) {
    event.preventDefault();
    let form;
    if (editor === undefined){
        form = formTask.serialize();
    }else {
        editor.save();
        form = formTask.serialize();
    }

    $.ajax({
        url: '/user/user-task',
        method: 'post',
        data: form,
        success: function (data) {
            if (data.isResultTask){
                handlerPositive(data);
            } else {
                handlerNegative(data);
            }
        }
    });

});



function handlerPositive(data) {
    let task = data.templates;
    let ptr = "";
    if (task.isQuestion){

        ptr = "<p class='positive'><strong>" + data.message + "</strong><a href='" + ordinalByLessonTopic(task.lessonTopic) + "'>--> Дальше...</a></p>" +
            "<p class='positive'><a href='/user/nav/lessons/step/"+task.id+"'>--> Решить снова</a></p>";

    } else {
        ptr = "<p class='positive'><strong>" + data.message + "</strong><a href='" + ordinalByLessonTopic(task.lessonTopic) + "'>-->  Дальше...</a></p>" +
            "<p class='positive'><a href='/user/nav/lessons/step/"+task.id+"'>--> Решить снова</a></p>";
    }
    answerUser.html(ptr);
    document.getElementById('buttonForm').style.visibility = 'hidden';
}



function ordinalByLessonTopic(topic){
    let ordinal = "";
        $.ajax({
        url: '/user/ordinal/' + topic,
        method: 'get',
        async: false,
        success: function (ord) {
            ordinal +=  '/user/nav/lessons/' + ord;

        }
    });
        return ordinal;
}

function handlerNegative(data) {
    let task = data.templates;
    let user = data.user;
    let ptr = "";
    if (task.isQuestion){

        ptr = "<p class='negative'><strong>" + data.message + "</strong><a href='/user/nav/lessons/step/"+task.id+"'>--> Решить снова</a></p>";

    } else {
        ptr = "<p class='negative'><strong>" + data.message + "</strong><a href='/user/nav/lessons/step/"+task.id+"'>--> Решить снова</a></p>";
    }
    answerUser.html(ptr);
    document.getElementById('buttonForm').style.visibility = 'hidden';
}

function shuffle(array) {
    for (let i = array.length - 1; i > 0; i--) {
        let j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}


function onchangeCheckbox(id) {
    let chbox = document.getElementById('answer' + id);
    if (chbox.checked) {
        answer[id] = chbox.valueOf();

    }
}

function isEmpty(str) {
    return (!str || str.length === 0 );
}



// editor.setValue("");
// editor.setValue(obj.scriptCode);






