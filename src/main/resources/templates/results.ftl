<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>ALCONSO</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.min.css">
    <script src="../webjars/jquery/jquery.min.js"></script>
    <script src="../webjars/sockjs-client/sockjs.min.js"></script>
    <script src="../webjars/stomp-websocket/stomp.min.js"></script>
    <script src="../app.js"></script>
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
                <div class="container" style="margin: 0;margin-top: 15px;margin-bottom: 5px;">
                    <div class="row" style="padding-left: 20px;padding-right: 20px;">
                        <div class="col d-flex d-lg-flex justify-content-start justify-content-lg-start">
                            <h3 class="text-start">Геномов всего:</h3>
                        </div>
                        <div class="col">
                            <h3 class="text-end d-flex d-lg-flex justify-content-end justify-content-lg-end" id="genomes_all">
                                <#if info??>${info.getGenomes_all()}<#else >0</#if>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="container" style="margin: 0;margin-top: 20px;">
                    <div class="row" style="padding-left: 20px;padding-right: 20px;">
                        <div class="col d-lg-flex justify-content-lg-start">
                            <h3 class="text-start d-flex justify-content-start">Геномов загружено:</h3>
                        </div>
                        <div class="col">
                            <h2 class="text-end d-flex d-lg-flex justify-content-end justify-content-lg-end" id="genomes_downloaded">
                                <#if info??>${info.getGenomes_downloaded()}<#else >0</#if>
                            </h2>
                        </div>
                    </div>
                    <div class="progress"  style="margin-right: 20px;margin-left: 20px;margin-bottom: 20px;">
                        <div class="progress-bar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="
                                width: <#if info??&&info.getGenomes_all()!=0>${(info.getGenomes_downloaded()/info.getGenomes_all()*100.0)?replace(",",".")}<#else >0</#if>%;" id="genomes_downloaded_scale">
                            <#if info??&&info.getGenomes_all()!=0>${info.getGenomes_downloaded()/info.getGenomes_all()*100.0}<#else >0</#if>%</div>
                    </div>
                </div>
                <div class="container" style="margin: 0;margin-top: 20px;">
                    <div class="row" style="padding-right: 20px;padding-left: 20px;">
                        <div class="col d-lg-flex justify-content-lg-start">
                            <h3 class="text-start d-flex justify-content-start">Геномов проигнорировано:</h3>
                        </div>
                        <div class="col">
                            <h3 class="text-end d-flex d-lg-flex justify-content-end justify-content-lg-end" id="genomes_ignored">
                                <#if info??>${info.getGenomes_ignored()}<#else >0</#if>
                            </h3>
                        </div>
                    </div>
                    <div class="progress"  style="color: var(--bs-red);margin-left: 20px;margin-bottom: 20px;margin-right: 20px;">
                        <div class="progress-bar bg-warning" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:
                        <#if info??&&info.getGenomes_all()!=0>${(info.getGenomes_ignored()*100.0/info.getGenomes_all())?replace(",",".")}<#else >0</#if>%;" id="genomes_ignored_scale">
                            <#if info??&&info.getGenomes_all()!=0>${info.getGenomes_ignored()*100.0/info.getGenomes_all()}<#else >0</#if>%
                        </div>
                    </div>
                </div>

                <div class="container" style="margin: 0;margin-top: 20px;">
                    <div class="row" style="padding-right: 20px;padding-left: 20px;">
                        <div class="col d-lg-flex justify-content-lg-start">
                            <h3 class="text-start d-flex justify-content-start">Цепочек проанализировано:</h3>
                        </div>
                        <div class="col">
                            <h3 class="text-end d-flex d-lg-flex justify-content-end align-items-center justify-content-lg-end" id="genomes_analysed">
                                <#if info??>${info.getGenomes_analysed()}<#else >0</#if>
                            </h3>
                        </div>
                    </div>
                    <div class="progress"  style="margin-left: 20px;margin-bottom: 20px;margin-right: 20px;">
                        <div class="progress-bar bg-info" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:
                        <#if info??&&info.getUnique_sequences()!=0>${(info.getGenomes_analysed()*100.0/info.getUnique_sequences())?replace(",",".")}<#else >0</#if>%;" id="genomes_analysed_scale">
                            <#if info??&&info.getUnique_sequences()!=0>${(info.getGenomes_analysed()*100.0/info.getUnique_sequences())}<#else >0</#if>%
                        </div>
                    </div>
                </div>
                <div class="container" style="margin: 0;margin-top: 15px;margin-bottom: 5px;">
                    <div class="row" style="padding-left: 20px;padding-right: 20px;">
                        <div class="col d-flex d-lg-flex justify-content-start justify-content-lg-start">
                            <h3 class="text-start">Цепочек восстановлено:</h3>
                        </div>
                        <div class="col">
                            <h3 class="text-end d-flex d-lg-flex justify-content-end justify-content-lg-end" id="sequence_restored">
                                <#if info??&&info.getGenomes_all()!=0>${info.getSequence_restored()}<#else >0</#if>
                            </h3>
                        </div>
                    </div>
                </div>
                    <div>
                        <h2 class="text-start" id="status">
                            <#if info??>${info.genome_status}</#if></h2>
                    </div>
            </div>
        </div>
        <div class="row mb-5" style="margin-top: 20px;">
            <form action="/download">
            <div class="col-md-8 col-xl-6 text-center mx-auto"><button class="btn btn-outline-secondary btn-lg" type="submit" style="padding: 10px 25px; <#if info??&&info.getGenome_status()!="DONE">visibility: hidden;</#if>" id="button">Загрузить результат</button></div>
            </form>
        </div>
    </div>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/script.min.js"></script>
</body>

</html>