<?php
    function darAlarma($node, $temperature, $humidity, $flame, $time){
        
        include("sendemail.php");//Mando a llamar la funcion que se encarga de enviar el correo electronico
        
        /*Configuracion de variables para enviar el correo*/
        $mail_username="alarm.fire.sos@gmail.com";  //Correo electronico saliente ejemplo: tucorreo@gmail.com
        $mail_userpassword="TheITCrowd";            //Tu contraseña de gmail
        $mail_addAddress="gabriavoz@gmail.com";     //correo electronico que recibira el mensaje
        $mail_setFromEmail="alarm.fire.sos@gmail.com";
        $mail_setFromName="MySQL";
        $mail_subject="Fire!!";
        $message = file_get_contents("mail_response.html");
        $message = str_replace('numNodo', $node, $message);
        $message = str_replace('valorTemp', $temperature, $message);
        $message = str_replace('valorHum', $humidity, $message);
        $message = str_replace('valorLlama', $flame, $message);
        $message = str_replace('ValorTiempo', $time, $message);
         
        echo $message;
         
        sendemail($mail_username,$mail_userpassword,$mail_setFromEmail,$mail_setFromName,$mail_addAddress,$mail_subject,$message);//Enviar el mensaje
    }
?>