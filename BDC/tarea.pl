%Parques
%Parque Daniel Hernández(13.674020416029846, -89.28877776769322)
%Parque San Martín(13.674249632786998, -89.28508696769568)
%Parque deportivo El Cafetalón(13.676939101610206, -89.28184656514244)
%Parque de la Familia(13.68283967443913, -89.28246846643609)
%Parque Colonia Monte Sión(13.68334024744733, -89.29032248751851)

%Centro comerciales
%La Skina(13.677975677004623, -89.29765137922647)
%Centro Comercial Las Ramblas(13.671707761345157, -89.26835016979297)
%Plaza Merliot(13.679065920807803, -89.27542338006855)
%Centro Comercial, Daniel Hernandez(13.674896195844797, -89.28852024564111)
%Centro Comercial Santa Inés(13.672665219344523, -89.2863745185677)

%Gasolineras
%UNO • Santa Tecla (13.672237397836605, -89.28350846707578)
%TEXACO • RV Santa Tecla (13.67290892399382, -89.29744488403495)
%UNO • Merliot (13.680074058974833, -89.27735201682775)
%TEXACO • SERVITEX (13.672412243776643, -89.27559180358621)
%TEXACO • La Skina (13.67856227438191, -89.29674636504643)

%Hospitales
%Hospital Climosal(13.672004569590136, -89.29237148330897)
%Hospital Policlínica Casa de Salud (13.677529726898635, -89.29033292525632)
%Hospital Nacional San Rafael (13.671462053675185, -89.27835950706266)
%Clínica Médica Maria Auxiliadora(13.676382994818589, -89.28621309508257)
%Hospital Seguro Social Santa Tecla(13.68032354743866, -89.28475373521991)

%Centros Escolares
%Centro Escolar "Marcelino García Flamenco"(13.672963474955276, -89.28035526318007)
%Escuela de Educación Especial de Santa Tecla(13.675319576622973, -89.2831018113428)
%Centro Escolar “Daniel Hernández”(13.673922778887961, -89.29063349996844)
%Colegio Madre Teresa Margarita Sánchez(13.672630124882694, -89.29479630612649)
%Complejo Educativo Walter A. Soundy(13.680281390735583, -89.27986135878199)

%Unir la lista de todos los parques, centrosEstudio, etc, a solo lugar.
%parques
lugar(parqueDanielHernandez).
lugar(elCafetalon).
lugar(parqueLaFamilia).
lugar(parqueSanMartin).
lugar(parqueColoniaMonteSion).

%centro comerciales
lugar(centroComercialLaSkina).
lugar(centroComercialLasRamblas).
lugar(plazaMerliot).
lugar(centroComercialSantaInes).
lugar(centroComercialDanielHernandez).

%gasolinera
lugar(gasolineraUnoSantaTecla).
lugar(texacoRvSantaTecla).
lugar(gasolineraUnoMerliot).
lugar(texacoServitex).
lugar(texacoLaSkina).

%hospitales
lugar(hospitalClimosal).
lugar(policlinicaCasaDeSalud).
lugar(hospitalSanRafael).
lugar(hospitalMariaAuxiliadora).
lugar(seguroSocial).

%centro de estudios
lugar(centroEscolarMarcelinoGarciaFlamenco).
lugar(educacionEspecial).
lugar(colegioDanielHernandez).
lugar(colegioMadreTeresaMargaritaSanchez).
lugar(complejoEducativoWalterASoundy).
lugar(itca).
lugar(liceoFrances).

%relaciones de los lugares
% irDesdeHacia/2 el primer argumento es desde y el segundo es hacia
irDesdeHacia(parqueDanielHernandez,parqueSanMartin).
irDesdeHacia(parqueSanMartin,parqueDanielHernandez).

irDesdeHacia(parqueSanMartin,elCafetalon).
irDesdeHacia(elCafetalon,parqueSanMartin).

irDesdeHacia(parqueLaFamilia,elCafetalon).
irDesdeHacia(elCafetalon,parqueLaFamilia).

irDesdeHacia(gasolineraUnoSantaTecla,centroComercialLaSkina).
irDesdeHacia(centroComercialLaSkina,gasolineraUnoSantaTecla).

irDesdeHacia(hospitalSanRafael, centroComercialLasRamblas).
irDesdeHacia(centroComercialLasRamblas, hospialSanRafael).

irDesdeHacia(texacoRvSantaTecla, centroComercialLaSkina).
irDesdeHacia(centroComercialLaSkina, texacoRvSantaTecla).

irDesdeHacia(gasolineraUnoSantaTecla,texacoRvSantaTecla).
irDesdeHacia(texacoRvSantaTecla,gasolineraUnoSantaTecla).

