package org.example.lobby.web.controllers.impl;


import org.example.lobby.web.controllers.ExceptionController;
import org.example.lobby.web.exceptions.model.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerImpl implements ExceptionController {

  static final Logger log = LoggerFactory.getLogger(ExceptionControllerImpl.class);

  @Override
  public ErrorInfo handleException(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @Override
  public ErrorInfo handleRestClientException(HttpServletRequest req, Exception ex) {
    log.error("Error from internal service by url: " + req.getRequestURL().toString() +
        "; exception message: " + ex.getMessage());
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @Override
  public ErrorInfo handleSecurityException(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @Override
  public ErrorInfo handleEmptyPageException(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @Override
  public ErrorInfo handleNoInternalGameIdInDBException(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @Override
  public ErrorInfo handleGenerateHashException(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }


}
