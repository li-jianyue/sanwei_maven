/*
 * This code is in the public domain.
 */

package com.koolearn.klibrary.api;

import com.koolearn.klibrary.api.ApiObject;

interface ApiInterface {
	ApiObject request(int method, in ApiObject[] parameters);
	List<ApiObject> requestList(int method, in ApiObject[] parameters);
	Map requestMap(int method, in ApiObject[] parameters);
}
