package com.gmail.dimaliahov.model;

public enum Status {
	ACTIVE, 		// User може бути з таким статусом він нормальний
	NOT_ACTIVE,		// User може бути з таким статусом він видалений

	CONSIDERATION, 	// Lesson може бути з таким статусом - створена студентом - попадає на розгляд вчителю
	APPROVE,    	// Lesson може бути з таким статусом - Підтверджена вчителем
	REJECTED		// Lesson може бути з таким статусом - Відхилена вчителем

}
