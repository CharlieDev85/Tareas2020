INSERT INTO grado (id, nombre_corto, nombre) VALUES (1, '1ero', 'Primero Basico');
INSERT INTO grado (id, nombre_corto, nombre) VALUES (2, '2do', 'Segundo Basico');
INSERT INTO grado (id, nombre_corto, nombre) VALUES (3, '3ero', 'Tercero Basico');

INSERT INTO profesor (id, nombre) VALUES (1, 'Profe Charlie');

INSERT INTO materia (grado_id, materia_grado, nombre, profesor_id, id) VALUES (1, 'matem1', 'matematica', 1,   1);
INSERT INTO materia (grado_id, materia_grado, nombre, profesor_id, id) VALUES (1, 'idioma1', 'idioma espanol',  null, 2);
INSERT INTO materia (grado_id, materia_grado, nombre, profesor_id, id) VALUES (1, 'ciencias1',  'ciencias naturales', null, 3);
INSERT INTO materia (grado_id, materia_grado, nombre, profesor_id, id) VALUES (1, 'sociales1', 'estudios sociales',  null, 4);

INSERT INTO tutor (id, nombre) VALUES (1, 'Tutor Azo');
INSERT INTO tutor (id, nombre) VALUES (2, 'Tuto R. Cito');

INSERT INTO bimestre (id, nombre) VALUES (1, 'Feb-Mar');
INSERT INTO bimestre (id, nombre) VALUES (2, 'Apr-May');
INSERT INTO bimestre (id, nombre) VALUES (3, 'Jun-Jul');
INSERT INTO bimestre (id, nombre) VALUES (4, 'Ago-Sep');

INSERT INTO ALUMNO (id, nombre, grado_id, tutor_id) VALUES (1, 'fulanito deTal', 1, 1);

INSERT INTO TIPO_TAREA (id, nombre) VALUES (1,'examen final');
INSERT INTO TIPO_TAREA (id, nombre) VALUES (2,'trabajo de zona');

INSERT INTO TAREA (id, tema, instrucciones, materia_id, tipo_tarea_id, punteo_maximo, fecha_de_entrega, bimestre_id)
VALUES (1, 'Factorizacion General', 'Realizar los siguientes ejercicios', 1, 1, 60, '2020-06-30', 1);


