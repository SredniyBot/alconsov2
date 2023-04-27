<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>ALCONSO</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.min.css">
      <link rel="icon" type="image/x-icon" href="../assets/fav.ico">
</head>

<body>
    <div class="container py-4 py-xl-5">
        <div class="row mb-5">
            <div class="col-md-8 col-xl-6 text-center mx-auto">
                <h1 style="margin-top: 20px;">ALCONSO</h1>
                <p class="w-lg-50">алгоритм поиска консервативных последовательностей в геномах вирусных организмов&nbsp;</p>
            </div>
        </div>
        <div class="row gx-1 gy-1 row-cols-1 row-cols-md-2 row-cols-xl-3 text-center d-md-flex d-lg-flex justify-content-md-center justify-content-lg-center align-items-lg-center">
            <div class="col d-lg-flex flex-column flex-fill align-items-lg-center" style="padding: 0;">


                <form method="post" action="/results">
                    <div class="row">
                        <div class="col">
                            <div class="container" style="margin: 0;">
                                <div class="row" style="padding-left: 20px;padding-right: 20px;">
                                    <div class="col d-flex d-md-flex flex-wrap justify-content-md-center align-items-md-center" style="margin-bottom: 10px;">
                                        <h3 class="text-start d-sm-flex d-md-flex justify-content-sm-center justify-content-md-start align-items-md-center" style="margin-top: 10px;margin-bottom: 10px;">Путь до папки с файлами</h3>


                                        <input class="form-control d-lg-flex flex-fill justify-content-lg-center align-items-lg-center" type="text" id="destination" name="destination" style="width: 400px;margin-left: 40px;" placeholder="F:\destination\get" minlength="1">
                                    </div>
                                </div>
                            </div>
                            <div class="container" style="margin: 0;">
                                <div class="row" style="padding-left: 20px;padding-right: 20px;">
                                    <div class="col d-flex d-lg-flex justify-content-start justify-content-lg-start">
                                        <h3 class="text-start">Разброс в результате:</h3>
                                    </div>
                                    <div class="col">
                                        <h3 class="text-end d-flex d-lg-flex justify-content-end justify-content-lg-end" id="scatter_value">7%</h3>
                                    </div>
                                </div>


                                <input class="form-range form-control" type="range" id="scatter" min="3" max="75" step="1" value="7" name="scatter" onchange="document.getElementById(&#39;scatter_value&#39;).innerHTML=this.value+&quot;%&quot;" oninput="document.getElementById(&#39;scatter_value&#39;).innerHTML=this.value+&quot;%&quot;" style="padding-right: 20px;padding-left: 20px;">
                            </div>
                            <div class="container" style="margin: 0;">
                                <div class="row" style="padding-left: 20px;padding-right: 20px;">
                                    <div class="col d-md-flex justify-content-md-center align-items-md-center" style="margin-top: 15px;margin-bottom: 20px;">
                                        <h3 class="text-start d-md-flex justify-content-md-start align-items-md-center" style="margin-top: 10px;margin-right: 40px;margin-bottom: 10px;margin-left: 40px;">Использовать геномы с 'n'</h3>


                                        <input type="checkbox" id="useN" name="useN"  style="width: 30px;height: 30px;">
                                    </div>
                                </div>
                            </div>

                            <button class="btn btn-secondary btn-lg" id="start" type="submit">Start</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
<#if error??>
            <div class="row mb-5 " style="margin-top: 20px;">
                <div class="col-md-8 col-xl-6 text-center mx-auto alert alert-danger">
                    <h4 >${error}</h4>
                </div>
            </div>
</#if>
    </div>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/script.min.js"></script>
</body>

</html>