window.addEventListener("load", function(){


        // XHRの宣言
        var XHR = new XMLHttpRequest();
        XHR.open("POST", "todo_diary/?action=Task&command=show", true);
         XHR.send();
        // 正常終了したらデータを取得する
        XHR.onreadystatechange = function(){
            if(XHR.readyState == 4 && XHR.status == 200){

                 var response = XHR.responseText;
                showTodo(response);



            }
        };




    document.getElementById("send_addTodo").addEventListener("click", function(){

        const textbox = document.getElementById('content');
        if(textbox.value != ""){
            const request = "content=" + textbox.value;
            // XHRの宣言
            var XHR = new XMLHttpRequest();

            XHR.open("POST", "todo_diary/?action=Task&command=create", true);
            // サーバに対して解析方法を指定する
            XHR.setRequestHeader( 'Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8' );

            // 送信を実行する
            XHR.send(request);
            // 正常終了したらデータを取得する
            XHR.onreadystatechange = function(){
                if(XHR.readyState == 4 && XHR.status == 200){
                    // POST送信した結果を表示する

                    document.getElementById('content').value = "";
                     var response = XHR.responseText;

                    showTodo(response);
                    //フラッシュを表示
                    const flash = document.getElementById("todo_flash");
                    flash.innerHTML ="<p>ADD SUCCESS !</p>"
                    setTimeout(function(){
                        eraseFlash(flash);
                    }, 2000);
         }

     }

        };
    }, false);



}, false);



//取得した文字列をIdとcontentに分けて表示
function showTodo(response) {
    if(response == "[]"){
            var todo_list = document.getElementById("todos");
                //todo_listにtodoを表示
                todo_list.innerHTML = "<div id=\"no_task\"><li>\n"
                + "<span>NO TASK</span>\n"
                +"<li></div>";


    } else {



                response = response.replace("[", "").replace("]","").replace(/\s+/g, "").split(",");
                var todos = [];




                response.forEach(todo => {
                   var t = todo.split(":");
                   todos.push(t);
                });

                var todo_list = document.getElementById("todos");
                //todo_listにtodoを表示
                todo_list.innerHTML = "";
                for (var i =0; i<todos.length; i++ ) {
                    todo_list.insertAdjacentHTML("beforeend", "<div id=\"" + todos[i][0] + "\">\n<li >\n"
                    + "<div class=\"btns\">\n"
                + "<button type=\"button\" class=\"deleteTodo\" value=\"" + todos[i][0] + "\" ><i class=\"far fa-trash-alt\"></i></i></button>\n"
                +"</div>\n"
                + "<span>" + todos[i][1]  + "</span>\n"
                + "<div class=\"btns\"> \n"
                + "<button type=\"button\" class=\"finishTodo\" value=\"" + todos[i][0] + "\"><i class=\"fas fa-check\"></i></button>\n"
                +"</div>\n"
                + "</li>\n</div>\n");
                }
                                   //deleteTodoボタンがクリックされたときのイベントを追加
               const delBtns = document.querySelectorAll(".deleteTodo");
               delBtns.forEach((x,y)=>{
                    x.addEventListener('click',()=>{
                        console.log("delete dayo");
                                 const todos = document.getElementsByClassName("deleteTodo");
                                 const todo = todos[y].value;

                                 deleteTodo(todo);

                });
            });

                         //finishTodoボタンがクリックされた時のイベントを追加
               const finBtns = document.querySelectorAll(".finishTodo");
                finBtns.forEach((x,y)=>{
                    x.addEventListener('click',()=>{
                                 const todos = document.getElementsByClassName("finishTodo");
                                 const todo = todos[y].value;

                                finishTodo(todo);

                });
            });
            }

 }
function deleteTodo(todo){
    const request = "id="+todo;
     console.log(request);
     // XHRの宣言
     var XHR = new XMLHttpRequest();

    XHR.open("POST", "todo_diary/?action=Task&command=destroy", true);

    // サーバに対して解析方法を指定する
    XHR.setRequestHeader( 'Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8' );
     // 送信を実行する
    XHR.send(request);
     // 正常終了したらデータを取得する
    XHR.onreadystatechange = function(){
        if(XHR.readyState == 4 && XHR.status == 200){
                document.getElementById('content').value = "";
                     var response = XHR.responseText;
                                 const flash = document.getElementById(todo);
                                 flash.innerHTML ="<li style=\"background-color: #f8d7da; \">\n"
                                                              + "<span style=\"  color: #999999ff; text-align:center; width: 100%;\">"
                                                              + "TASK DELETED! </span>\n"
                                                              +"</li>";
                                 setTimeout(function(){
                      showTodo(response);
                    }, 1000);

        }
    };
}

function finishTodo(todo){

      const request = "id="+todo;

       // XHRの宣言
       var XHR = new XMLHttpRequest();

       XHR.open("POST", "todo_diary/?action=Task&command=complete", true);

       // サーバに対して解析方法を指定する
       XHR.setRequestHeader( 'Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8' );
        // 送信を実行する
       XHR.send(request);
        // 正常終了したらデータを取得する
        XHR.onreadystatechange = function(){
            if(XHR.readyState == 4 && XHR.status == 200){
                     document.getElementById('content').value = "";
                     var response = XHR.responseText;

                                 const flash = document.getElementById(todo);

                                 flash.innerHTML ="<li style=\"background-color: #d4edda; \">\n"
                                                              + "<span style=\"  color: #999999ff; text-align:center; width: 100%;\">"
                                                              + "TASK COMPLETED! </span>\n"
                                                              +"</li>";
                                 setTimeout(function(){
                                    showTodo(response);
                                 }, 1000);

            }
      };
}

function eraseFlash(flash){
    flash.innerHTML="";
}