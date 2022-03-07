package com.mobiquity.transfer.function.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LambdaResponse {

  private Integer httpStatusCode;
  private Object body;
  private List<String> errors = new ArrayList<>();

}
