package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.apache.commons.collections.functors.TruePredicate;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockedTS1;
  private TorpedoStore mockedTS2;


  @BeforeEach
  public void init(){
  mockedTS1 = mock(TorpedoStore.class);
  mockedTS2 = mock(TorpedoStore.class);

  

    this.ship = new GT4500(mockedTS1, mockedTS2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockedTS1.fire(1)).thenReturn(true);
   when(mockedTS2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockedTS1, times(1)).fire(1);
    //verify(mockedTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockedTS1.fire(1)).thenReturn(true);
    when(mockedTS2.fire(1)).thenReturn(true);
    when(mockedTS1.isEmpty()).thenReturn(false, false, false, true);
    when(mockedTS2.isEmpty()).thenReturn(false, false, false, true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockedTS1, times(3)).fire(1);
    verify(mockedTS2, times(3)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos3_Single_Success(){
    // Arrange
    when(mockedTS1.fire(1)).thenReturn(true);
    when(mockedTS2.fire(1)).thenReturn(true);
    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result3 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    assertEquals(true, result3);
    verify(mockedTS1, times(2)).fire(1);
    verify(mockedTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos4_Single_SecondaryContainsOnly1(){
    // Arrange
    when(mockedTS1.fire(1)).thenReturn(true);
    when(mockedTS2.fire(1)).thenReturn(true, false);

    when(mockedTS1.isEmpty()).thenReturn(false);
    when(mockedTS2.isEmpty()).thenReturn(false, true);
    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result3 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result4 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    assertEquals(true, result3);
    assertEquals(true, result4);
    verify(mockedTS1, times(3)).fire(1);
    verify(mockedTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos3_Single_FailureAfter1(){
    // Arrange
    when(mockedTS1.fire(1)).thenReturn(true);
    when(mockedTS2.fire(1)).thenReturn(false);

    when(mockedTS1.isEmpty()).thenReturn(false);
    when(mockedTS2.isEmpty()).thenReturn(false, true);
    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result3 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(false, result2);
    assertEquals(true, result3);

    verify(mockedTS1, times(2)).fire(1);
    verify(mockedTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success_3_7(){
    // Arrange
    when(mockedTS1.fire(1)).thenReturn(true);
    when(mockedTS2.fire(1)).thenReturn(true);
    when(mockedTS1.isEmpty()).thenReturn(false, false, false, true);
    when(mockedTS2.isEmpty()).thenReturn(false, false, false,false, false, false,false, true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockedTS1, times(3)).fire(1);
    verify(mockedTS2, times(7)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_OneFails_3_7(){
    // Arrange
    when(mockedTS1.fire(1)).thenReturn(true);
    when(mockedTS2.fire(1)).thenReturn(true, true, false, true);
    when(mockedTS1.isEmpty()).thenReturn(false, false, false, true);
    when(mockedTS2.isEmpty()).thenReturn(false, false, false, false, false, false,false, true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockedTS1, times(3)).fire(1);
    verify(mockedTS2, times(7)).fire(1);
    assertEquals(true, result);
  }

}
