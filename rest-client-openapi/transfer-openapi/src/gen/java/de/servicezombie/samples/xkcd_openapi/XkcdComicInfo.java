package de.servicezombie.samples.xkcd_openapi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-11-11T22:13:25.535093800+01:00[Europe/Berlin]")
public class XkcdComicInfo   {
  
  private @Valid Integer day;
  private @Valid Integer month;
  private @Valid Integer year;
  private @Valid String safeTitle;
  private @Valid String img;

  /**
   **/
  public XkcdComicInfo day(Integer day) {
    this.day = day;
    return this;
  }

  

  
  @ApiModelProperty(value = "")
  @JsonProperty("day")
  public Integer getDay() {
    return day;
  }

  public void setDay(Integer day) {
    this.day = day;
  }/**
   **/
  public XkcdComicInfo month(Integer month) {
    this.month = month;
    return this;
  }

  

  
  @ApiModelProperty(value = "")
  @JsonProperty("month")
  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }/**
   **/
  public XkcdComicInfo year(Integer year) {
    this.year = year;
    return this;
  }

  

  
  @ApiModelProperty(value = "")
  @JsonProperty("year")
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }/**
   **/
  public XkcdComicInfo safeTitle(String safeTitle) {
    this.safeTitle = safeTitle;
    return this;
  }

  

  
  @ApiModelProperty(value = "")
  @JsonProperty("safe_title")
  public String getSafeTitle() {
    return safeTitle;
  }

  public void setSafeTitle(String safeTitle) {
    this.safeTitle = safeTitle;
  }/**
   **/
  public XkcdComicInfo img(String img) {
    this.img = img;
    return this;
  }

  

  
  @ApiModelProperty(value = "")
  @JsonProperty("img")
  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XkcdComicInfo xkcdComicInfo = (XkcdComicInfo) o;
    return Objects.equals(this.day, xkcdComicInfo.day) &&
        Objects.equals(this.month, xkcdComicInfo.month) &&
        Objects.equals(this.year, xkcdComicInfo.year) &&
        Objects.equals(this.safeTitle, xkcdComicInfo.safeTitle) &&
        Objects.equals(this.img, xkcdComicInfo.img);
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, month, year, safeTitle, img);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class XkcdComicInfo {\n");
    
    sb.append("    day: ").append(toIndentedString(day)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    safeTitle: ").append(toIndentedString(safeTitle)).append("\n");
    sb.append("    img: ").append(toIndentedString(img)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

