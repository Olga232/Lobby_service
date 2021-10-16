package org.example.lobby.web.controllers;

import org.example.lobby.web.exceptions.EmptyPageException;
import org.example.lobby.web.exceptions.GenerateHashException;
import org.example.lobby.web.exceptions.NoInternalGameIdInDBException;
import org.example.lobby.web.exceptions.model.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;

public interface ExceptionController {

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorInfo handleException(HttpServletRequest req, Exception ex);

  @ExceptionHandler(value = RestClientException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorInfo handleRestClientException(HttpServletRequest req, Exception ex);

  @ExceptionHandler(value = SecurityException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorInfo handleSecurityException(HttpServletRequest req, Exception ex);

  @ExceptionHandler(value = EmptyPageException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  ErrorInfo handleEmptyPageException(HttpServletRequest req, Exception ex);

  @ExceptionHandler(value = NoInternalGameIdInDBException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  ErrorInfo handleNoInternalGameIdInDBException(HttpServletRequest req, Exception ex);

  @ExceptionHandler(value = GenerateHashException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorInfo handleGenerateHashException(HttpServletRequest req, Exception ex);


}
