/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package readingbinaryfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** JDK 7+. */
public class SmallBinaryFiles 
{
  final static String FILE_NAME = "D:\\proyectos\\AXA\\20170717_EjemplosDatosNotas_RG\\CamposLayOutNotas_31122017.csv";
  final static String OUTPUT_FILE_NAME = "D:\\proyectos\\AXA\\20170717_EjemplosDatosNotas_RG\\PruebasSanitizacion\\Notas_2017.csv";

  //final static String FILE_NAME = "D:\\proyectos\\AXA\\20170717_EjemplosDatosNotas_RG\\PruebasSanitizacion\\Notas_2017.csv";
  //final static String OUTPUT_FILE_NAME = "D:\\proyectos\\AXA\\20170717_EjemplosDatosNotas_RG\\PruebasSanitizacion\\Notas_2017_v.csv";


  
  public static void main(String... aArgs) throws IOException{
    long t0r,t1r, t0w,t1w, t0c,t1c;
    double deltaTr,deltaTw, deltaTc;
    byte[] convChar = new byte[256];
    int cuantosRemovidos;
    
    SmallBinaryFiles binary = new SmallBinaryFiles();
    
    t0r = System.nanoTime();
    byte[] bytes = binary.readSmallBinaryFile(FILE_NAME);
    t1r = System.nanoTime();
    deltaTr = ( t1r - t0r ) / 1.0e9;   
    log("Small - size of file read-in:" + bytes.length);
    log("Elapsed time:" + deltaTr + " seconds");

    t0c = System.nanoTime();
    carga_cod_arch_sal(convChar);
    cambiaChars(convChar,bytes);
    t1c = System.nanoTime();
    deltaTc = ( t1c - t0c ) / 1.0e9;  
    log("---------------------");
    log("Small - size char converter:" + bytes.length);
    log("Elapsed time:" + deltaTc + " seconds");
    
    cuantosRemovidos = quitaEmbebidos(bytes);
    
    t0w = System.nanoTime();
    binary.writeSmallBinaryFile(bytes, OUTPUT_FILE_NAME);
    t1w = System.nanoTime();
    deltaTw = ( t1w - t0w ) / 1.0e9;
    log("---------------------");
    log("Small - size of file write:" + bytes.length);
    log("Elapsed time:" + deltaTw + " seconds");
   
    cuentaCuantos(bytes);
    
    verificaformato(bytes);
    
    entersEnMedio(bytes);
    
    if(cuantosRemovidos > 0 )
        log("Se removieron " + cuantosRemovidos + " enters en los registros ");

    
  }

  byte[] readSmallBinaryFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
    return Files.readAllBytes(path);
  }
  
  void writeSmallBinaryFile(byte[] aBytes, String aFileName) throws IOException 
  {
  
    Path path = Paths.get(aFileName);
    Files.write(path, aBytes); //creates, overwrites
     
  }
  
  private static int quitaEmbebidos(byte b[])
  {
      int cuantosRemovidos = 0;
      long tam = b.length;
      int k;
      int comillas = '"';
      boolean ENTRE_COMILLAS = false;
      
      for(k = 0; k < tam; k++)
        if( ENTRE_COMILLAS )
        {
           if( b[k] == 10 || b[k] == 13 )
           {
               b[k]= 32;
               cuantosRemovidos++;
           }
           else
             if( b[k] == comillas ) ENTRE_COMILLAS = false;  
        }
        else
        {
            if( b[k] == comillas )
                ENTRE_COMILLAS = true;
        }      
      return cuantosRemovidos;
  }
  
  private static void cuentaCuantos( byte[] texto)
  {
      long c[] = new long[256];
      long tam = texto.length;
      int k, w;
      for(k = 0; k < 256; k++) c[k] = 0;
      for( k =0; k < tam; k++) 
      {
          w = (int) texto[k];
          w = w < 0 ? 256 + w : w;
          c[w]++;
      }
      
      for(k = 0; k < 256; k++)
          log("c[" + k + "]:" + c[k]);    
  }
  
  private static void log(Object aMsg){
    System.out.println(String.valueOf(aMsg));
  }
 
  private static void cambiaChars( byte[] conversor, byte texto[] )
  {
      long tam = texto.length;
      int k, w;
      for( k =0; k < tam; k++) 
      {
          w = (int) texto[k];
          w = w < 0 ? 256 + w : w;
          texto[k] = conversor[w];
      }
    
  }
  
  private static boolean entersEnMedio( byte b[])
  {
      long tam_menos_10 = b.length - 10;
      int k;
      int numSaltosErroneos = 0;
      int numLinea = 1;
      
      for( k = 0; k < tam_menos_10; k++)
      {
          if( b[k] == 13 )numLinea++;
          if( b[k] == 10 && b[k-1] != 13 ) 
          {
              numSaltosErroneos++;
              System.out.print("SaltoEmbebido num:" + numSaltosErroneos + " en el registro " + numLinea + " --->");
                    for( int z = -20; z < 20; z++)
                      System.out.print((char)b[k+z]);
                    System.out.println();
              
          }
      }
      
      return numSaltosErroneos > 0;
  }
  
  private static void verificaformato( byte[] c )
  {
      // verifico que después de un CRLF existe una "L"
      
      long tam, tam_menos_5;
      int k, z;
      int numLinea = 1;
      
      long numErrores = 0;
      
      tam = c.length;
      tam_menos_5 = tam - 5;
      for( k = 0; k < tam; k++ )
          if( (int) c[k] == 13 )
          {
              numLinea++;
              if( k < tam_menos_5 )
                  if( !checaInicio(c,k))
                  {    
                    numErrores++;
                    System.out.print("Error num:" + numErrores + " en el registro " + numLinea + " --->");
                    for(z = -20; z < 20; z++)
                      System.out.print((char)c[k+z]);
                    System.out.println();
                  }
          }
      
                  
      log("numErrores:" + numErrores );     
  }
  
  private static boolean checaInicio(byte[] b, int k)
  {
     boolean blnRes = true;
     int aMay = 'A', zMay = 'Z', cero = '0', nueve = '9';

     blnRes &= aMay <= b[k+2] && b[k+2] <= zMay;
     blnRes &= aMay <= b[k+3] && b[k+3] <= zMay;
     blnRes &= aMay <= b[k+11] && b[k+11] <= zMay;
    
     for( int j = k + 4; j < k + 11; j++ )blnRes &= cero <= b[j] && b[j] <= nueve;
     
     return blnRes;
  }
  
  private static void carga_cod_arch_sal( byte[] arr)
  {
    arr[0] = 0;
arr[1] = 32;
arr[2] = 32;
arr[3] = 32;
arr[4] = 32;
arr[5] = 32;
arr[6] = 32;
arr[7] = 32;
arr[8] = 32;
arr[9] = 32;
arr[10] = 10;
arr[11] = 32;
arr[12] = 32;
arr[13] = 13;
arr[14] = 32;
arr[15] = 32;
arr[16] = 32;
arr[17] = 32;
arr[18] = 32;
arr[19] = 32;
arr[20] = 32;
arr[21] = 32;
arr[22] = 32;
arr[23] = 32;
arr[24] = 32;
arr[25] = 32;
arr[26] = 32;
arr[27] = 32;
arr[28] = 32;
arr[29] = 32;
arr[30] = 32;
arr[31] = 32;
arr[32] = 32;
arr[33] = 33;
arr[34] = 34;
arr[35] = 35;
arr[36] = 36;
arr[37] = 37;
arr[38] = 38;
arr[39] = 39;
arr[40] = 40;
arr[41] = 41;
arr[42] = 42;
arr[43] = 43;
arr[44] = 44;
arr[45] = 45;
arr[46] = 46;
arr[47] = 47;
arr[48] = 48;
arr[49] = 49;
arr[50] = 50;
arr[51] = 51;
arr[52] = 52;
arr[53] = 53;
arr[54] = 54;
arr[55] = 55;
arr[56] = 56;
arr[57] = 57;
arr[58] = 58;
arr[59] = 59;
arr[60] = 60;
arr[61] = 61;
arr[62] = 62;
arr[63] = 63;
arr[64] = 64;
arr[65] = 65;
arr[66] = 66;
arr[67] = 67;
arr[68] = 68;
arr[69] = 69;
arr[70] = 70;
arr[71] = 71;
arr[72] = 72;
arr[73] = 73;
arr[74] = 74;
arr[75] = 75;
arr[76] = 76;
arr[77] = 77;
arr[78] = 78;
arr[79] = 79;
arr[80] = 80;
arr[81] = 81;
arr[82] = 82;
arr[83] = 83;
arr[84] = 84;
arr[85] = 85;
arr[86] = 86;
arr[87] = 87;
arr[88] = 88;
arr[89] = 89;
arr[90] = 90;
arr[91] = 91;
arr[92] = 92;
arr[93] = 93;
arr[94] = 94;
arr[95] = 95;
arr[96] = 96;
arr[97] = 97;
arr[98] = 98;
arr[99] = 99;
arr[100] = 100;
arr[101] = 101;
arr[102] = 102;
arr[103] = 103;
arr[104] = 104;
arr[105] = 105;
arr[106] = 106;
arr[107] = 107;
arr[108] = 108;
arr[109] = 109;
arr[110] = 110;
arr[111] = 111;
arr[112] = 112;
arr[113] = 113;
arr[114] = 114;
arr[115] = 115;
arr[116] = 116;
arr[117] = 117;
arr[118] = 118;
arr[119] = 119;
arr[120] = 120;
arr[121] = 121;
arr[122] = 122;
arr[123] = 123;
arr[124] = 124;
arr[125] = 125;
arr[126] = 126;
arr[127] = 32;
arr[128] = 32;
arr[129] = 32;
arr[130] = 32;
arr[131] = 32;
arr[132] = 32;
arr[133] = 32;
arr[134] = 32;
arr[135] = 32;
arr[136] = 32;
arr[137] = 32;
arr[138] = 32;
arr[139] = 32;
arr[140] = 32;
arr[141] = 32;
arr[142] = 32;
arr[143] = 32;
arr[144] = 32;
arr[145] = 32;
arr[146] = 32;
arr[147] = 32;
arr[148] = 32;
arr[149] = 32;
arr[150] = 32;
arr[151] = 32;
arr[152] = 32;
arr[153] = 32;
arr[154] = 32;
arr[155] = 32;
arr[156] = 32;
arr[157] = 32;
arr[158] = 32;
arr[159] = 32;
arr[160] = 32;
arr[161] = 32;
arr[162] = 32;
arr[163] = 32;
arr[164] = 32;
arr[165] = 32;
arr[166] = 32;
arr[167] = 32;
arr[168] = 32;
arr[169] = 32;
arr[170] = 32;
arr[171] = 32;
arr[172] = 32;
arr[173] = 32;
arr[174] = 32;
arr[175] = 32;
arr[176] = 32;
arr[177] = 32;
arr[178] = 32;
arr[179] = 32;
arr[180] = 32;
arr[181] = 32;
arr[182] = 32;
arr[183] = 32;
arr[184] = 32;
arr[185] = 32;
arr[186] = 32;
arr[187] = 32;
arr[188] = 32;
arr[189] = 32;
arr[190] = 32;
arr[191] = 32;
arr[192] = 97;
arr[193] = 97;
arr[194] = 97;
arr[195] = 97;
arr[196] = 97;
arr[197] = 97;
arr[198] = 97;
arr[199] = 32;
arr[200] = 101;
arr[201] = 101;
arr[202] = 101;
arr[203] = 101;
arr[204] = 105;
arr[205] = 105;
arr[206] = 105;
arr[207] = 105;
arr[208] = 32;
arr[209] = 110;
arr[210] = 111;
arr[211] = 111;
arr[212] = 111;
arr[213] = 111;
arr[214] = 111;
arr[215] = 32;
arr[216] = 111;
arr[217] = 117;
arr[218] = 117;
arr[219] = 117;
arr[220] = 117;
arr[221] = 32;
arr[222] = 32;
arr[223] = 32;
arr[224] = 97;
arr[225] = 97;
arr[226] = 97;
arr[227] = 97;
arr[228] = 97;
arr[229] = 97;
arr[230] = 97;
arr[231] = 32;
arr[232] = 101;
arr[233] = 101;
arr[234] = 101;
arr[235] = 101;
arr[236] = 105;
arr[237] = 105;
arr[238] = 105;
arr[239] = 105;
arr[240] = 111;
arr[241] = 110;
arr[242] = 111;
arr[243] = 111;
arr[244] = 111;
arr[245] = 111;
arr[246] = 111;
arr[247] = 32;
arr[248] = 32;
arr[249] = 117;
arr[250] = 117;
arr[251] = 117;
arr[252] = 117;
arr[253] = 32;
arr[254] = 32;
arr[255] = 32;

  }
}