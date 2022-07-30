<!DOCTYPE html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>       
        <link rel="stylesheet" href="css/404.css">
        <title>404</title>
    </head>

    <body>

        <jsp:include page="base-view/headerUser.jsp"></jsp:include>

        <!-- Intro Start -->

        <div id="intro">
            <div class="intro-child">
                <h1>Not Found</h1>
            </div>
        </div>

        <!-- Intro End -->

        <div class="container">
            <i class="fa-solid fa-triangle-exclamation"></i>
            <h1>404<h1>
            <p>We are sorry, the page you have looked for does not exist in our website! Maybe go to our home page or try to use a search?</p>
            <a href="home">Go to your home page</a>
        </div>
        
        <jsp:include page="base-view/footerUser.jsp"></jsp:include>
    </body>
    
</html>