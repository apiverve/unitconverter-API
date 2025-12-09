declare module '@apiverve/unitconverter' {
  export interface unitconverterOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface unitconverterResponse {
    status: string;
    error: string | null;
    data: UnitConverterData;
    code?: number;
  }


  interface UnitConverterData {
      result:          Result;
      unitDefinitions: UnitDefinitions;
  }
  
  interface Result {
      result: number;
      from:   string;
      to:     string;
  }
  
  interface UnitDefinitions {
      from: From;
      to:   From;
  }
  
  interface From {
      abbr:     string;
      measure:  string;
      system:   string;
      singular: string;
      plural:   string;
  }

  export default class unitconverterWrapper {
    constructor(options: unitconverterOptions);

    execute(callback: (error: any, data: unitconverterResponse | null) => void): Promise<unitconverterResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: unitconverterResponse | null) => void): Promise<unitconverterResponse>;
    execute(query?: Record<string, any>): Promise<unitconverterResponse>;
  }
}
